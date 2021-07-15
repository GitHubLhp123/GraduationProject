package myproject.project.utils.server;

import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class readFile {
    /**
     * 支持文件上传批量查询调度任务
     * @param file
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "file")
    public void export(@RequestParam("file") MultipartFile file,
                       HttpServletRequest request, HttpServletResponse response) {
        System.out.println("文件原始名称：" + file.getOriginalFilename());
        //解析文本后缀名
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        System.out.println("文件类型 即后缀名" + fileType);
        List<String> taskNames = new ArrayList<String>();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //根据文件类型选择不同的方法提取信息
            if ((fileType.equals("txt") || fileType.equals("csv")) && inputStream != null) {
                taskNames = this.readTxtOrCsv(inputStream);
            } else if (fileType.equals("xls") && inputStream != null) {
                taskNames = this.readXls(inputStream);
            } else if (fileType.equals("xlsx") && inputStream != null) {
                taskNames = this.readXlsx(inputStream);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        taskNames = taskNames.stream().distinct().collect(Collectors.toList());
        System.out.println(taskNames);
        StringBuffer returnJson=new StringBuffer();
        Gson gson=new Gson();
        returnJson.append("{  \"ids\":" + gson.toJson(taskNames) + " }") ;
        System.out.println(returnJson);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.println(returnJson);

        writer.flush();
        writer.close(); // 关闭输出流

    }

    public List<String> readTxtOrCsv(InputStream inputStream) {
        List<String> taskNames = new ArrayList<String>();
        try {
            InputStreamReader isr = new InputStreamReader(
                    inputStream, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String lineTxt = "";
            while ((lineTxt = br.readLine()) != null) {
                String[] arrStrings = lineTxt.split(","); // 用于把一个字符串分割成字符串数组
                taskNames.addAll(Arrays.asList(arrStrings));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return taskNames;
    }

    public List<String> readXlsx(InputStream inputStream) {
        List<String> taskNames = new ArrayList<String>();
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //解析excel的每个sheet
        for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
            XSSFSheet sheet = wb.getSheetAt(sheetNum);
            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }
            // 解析sheet的每一行的数据
            for (int rowNum = sheet.getFirstRowNum(); rowNum < sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (null == row) {
                    continue;
                }
                // 解析Row的每一列的数据
                for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
                    if (row.getCell(cellNum) != null && !row.getCell(cellNum).toString().equals("")) {
                        taskNames.add(row.getCell(cellNum).toString());
                    }
                }

            }
        }
        return taskNames;

    }


    public List<String> readXls(InputStream inputStream) {
        List<String> taskNames = new ArrayList<String>();
        try {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
            HSSFWorkbook wb = new HSSFWorkbook(poifsFileSystem);
            for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
                HSSFSheet sheet = wb.getSheetAt(sheetNum);
                // 校验sheet是否合法
                if (sheet == null) {
                    continue;
                }
                // 解析每一行的数据
                for (int rowNum = sheet.getFirstRowNum(); rowNum < sheet.getLastRowNum(); rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (null == row) {
                        continue;
                    }
                    for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {

                        if (row.getCell(cellNum) != null && !row.getCell(cellNum).toString().equals("")) {

                            taskNames.add(row.getCell(cellNum).toString());
                        }
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskNames;
    }
}
