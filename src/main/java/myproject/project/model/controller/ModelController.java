package myproject.project.model.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import myproject.project.model.entity.ModelEntity;
import myproject.project.model.entity.ModelFieldEntity;
import myproject.project.model.repository.ModelFieldRepository;
import myproject.project.model.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RequestMapping("/model")
@Controller
public class ModelController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ModelRepository modelRepository;
    @Autowired
    ModelFieldRepository modelFieldRepository;
    @Autowired
    Gson gson;


    @ResponseBody
    @GetMapping("/getModelList")
    public Map<String, Object> getModelList(
            @RequestParam(value = "modelName", defaultValue = "") String modelName,
            @RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "state", defaultValue = "") String state,
            @RequestParam(value = "lastupd", defaultValue = "") String lastupd,
            @RequestParam(value = "teamName", defaultValue = "") String teamName,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize

    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String startDate = "";
            String endDate = "";
            if (lastupd.equals("")) {
                endDate = "9";
            } else {
                String[] dateRange = lastupd.split(",");      //用来控制时间范围
                startDate = dateRange[0];
                endDate = dateRange[1];
            }

            Pageable pageable = PageRequest.of(pageNum - 1, pageSize);//分页属性
            List<ModelEntity> modelList = modelRepository.findAllByModelNameAndStateAndTAndTeamNameAndCreateDateBetween(modelName, userName, state, startDate, endDate, teamName, pageable);
            Integer modelListLength = modelRepository.countAllByModelNameAndStateAndTAndTeamNameAndCreateDateBetween(modelName, userName, state, startDate, endDate, teamName);
            map.put("data", modelList);
            map.put("dataLength", modelListLength);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }


    @ResponseBody
    @GetMapping("/getModelByModelId")
    public Map<String, Object> getModelByModelId(
            @RequestParam(value = "modelId", defaultValue = "") String modelId) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            ModelEntity modelEntity = modelRepository.findByModelId(modelId);
            map.put("data", modelEntity);
            map.put("status", 200);
            System.out.println(modelEntity);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }

        return map;
    }


    @ResponseBody
    @GetMapping("/getModelFieldListByModelId")
    public Map<String, Object> getModelFieldListByModelId(
            @RequestParam(value = "modelId", defaultValue = "") String modelId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<ModelFieldEntity> modelFieldEntityList = modelFieldRepository.findAllByModelId(modelId);
            map.put("data", modelFieldEntityList);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }

        return map;
    }

    @ResponseBody
    @GetMapping("/checkExistedModel")
    public Map<String, Object> checkExistedModel(
            @RequestParam(value = "modelName", defaultValue = "") String modelName,
            @RequestParam(value = "teamName", defaultValue = "") String teamName,
            @RequestParam(value = "dsName", defaultValue = "") String dsName,
            @RequestParam(value = "modelSchema", defaultValue = "") String modelSchema
    ) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("status", 200);
            //  model_name`,`ds_name`,`model_schema 三者联合起来为 unique
            ModelEntity modelEntity = modelRepository.findByModelNameAndDsNameAndModelSchema(modelName, dsName, modelSchema);

            if (modelEntity != null) {
                map.put("data", "模型已存在:数据源名:" + modelEntity.getDsName() + "_团队名:" + modelEntity.getTeamName());
                return map;
            }
            modelEntity = modelRepository.findByModelNameAndTeamNameAndDsName(modelName, teamName, dsName);
            //model_name,team_name二者联合起来为unique
            if (modelEntity != null) {
                map.put("data", "模型已存在:数据源名:" + modelEntity.getDsName() + "_团队名:" + modelEntity.getTeamName());
                return map;
            }

        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }


        return map;
    }

    @ResponseBody
    @GetMapping("/deleteModelByModelIdList")
    public Map<String, Object> deleteModelByModelIdList(
            @RequestParam(value = "modelIdList", defaultValue = "") String modelIdList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        try {
            System.out.println(modelIdList);
            for (String ele : modelIdList.split(",")) {
                //不要使用in来删除，确保删除时出现错误，立即返回
                modelRepository.deleteByModelId(ele);
                modelFieldRepository.deleteAllByModelId(ele);
            }

            map.put("data", "已删除");
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @PostMapping(value = "saveModel")
    public Map<String, Object> saveModel(@RequestBody Map<String, Object> body) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println(body);
        try {
            ModelEntity modelEntity = gson.fromJson(body.get("modelData").toString(), ModelEntity.class);//解析model的参数
            modelRepository.save(modelEntity);

            System.out.println("modelID" + modelEntity.getModelId() + "\r\n");
            List<ModelFieldEntity> modelFieldEntityList = gson.fromJson(body.get("columnData").toString(), new TypeToken<List<ModelFieldEntity>>() {}.getType());//解析model字段的参数

            for (ModelFieldEntity ele : modelFieldEntityList) {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");//uuid
                ele.setColumnId(uuid);
                ele.setModelName(modelEntity.getModelName());
                ele.setModelId(modelEntity.getModelId());
            }
            modelFieldRepository.deleteAllByModelId(modelEntity.getModelId());    //保存之前先删除原先的模型字段
            modelFieldRepository.saveAll(modelFieldEntityList);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        System.out.println(map);
        return map;
    }
}
