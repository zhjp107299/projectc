
package com.seven.utils;

/**
 * 这些方法用于获取Excel表中的数据（但是不将其存入数据库）
 */

import com.mysql.cj.util.StringUtils;
import com.seven.pojo.*;
import org.apache.ibatis.javassist.bytecode.CodeIterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.*;

public class FetchInfoFromExcel {
    private final static String excel2003L = ".xls";    //2003-版本的excel
    private final static String excel2007U = ".xlsx";    //2007+版本的excel


    /**
     * 获取单元格的值
     */
    public String getCellValue(Cell cell) {
        String value = "";
        //判断数据类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                value = ((int) cell.getNumericCellValue()) + "";
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }

    /**
     * 根据文件的后缀选择上传文件的版本
     *
     * @param file
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(String file, FileInputStream in) throws Exception {
        Workbook workbook = null;

        String fileVersion = file.substring(file.lastIndexOf("."));
        if (excel2003L.equals(fileVersion)) {
            workbook = new HSSFWorkbook(in);
        } else if (excel2007U.equals(fileVersion)) {
            workbook = new XSSFWorkbook(in);
        } else {
            throw new Exception("解析的文件格式有误");
        }
        return workbook;
    }

    /**
     * 从Excel表中获取教师表
     *
     * @param
     * @return
     * @throws IOException
     */


    public List<Teacher> getTeacherTableByExcel(String sFile, FileInputStream in) throws Exception {
        //list对象
        List<Teacher> list = new ArrayList<Teacher>();
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //创建excel表对象
        Workbook workbook = this.getWorkbook(sFile, in);
        //创建工作表对象
        sheet = workbook.getSheetAt(0);

         Integer tId = 0;//编号
         String tName = "";//姓名
         String tPassword = "";//密码
        String md5Password = "";

        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            //对行判空
            if (row == null) {
                continue;
            }
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                //对列判空
                cell = row.getCell(j);
                if (null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }
                switch (j) {
                    case 0:
                        String id = this.getCellValue(cell);
                        tId = Integer.parseInt(id);
                        break;
                    case 1:
                        tName = this.getCellValue(cell);
                        break;
                    case 2:
                        tPassword = this.getCellValue(cell);
                        MessageDigest m = MessageDigest.getInstance("MD5");
                        m.update(tPassword.getBytes("UTF-8"));
                        byte[] digest = m.digest();
                        md5Password = DigestUtil.convertToHexString(digest);
                        break;

                }

            }
            Teacher teacher = new Teacher(tId, tName, md5Password);
            list.add(teacher);
        }
        System.out.println("--->excel数据:TeacherTable执行成功！");
        return list;
    }


    /**
     * 从Excel表中获取学生表
     *
     * @param
     * @return
     * @throws IOException
     */


    public List<Student> getStudentTableByExcel(String sFile, FileInputStream in) throws Exception {
        //list对象
        List<Student> list = new ArrayList<Student>();
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //创建excel表对象
        Workbook workbook = this.getWorkbook(sFile, in);
        //创建工作表对象
        sheet = workbook.getSheetAt(0);

        Integer sId = 0;//学号
        String sName = "";//姓名
        String sGender = "";//性别
        String sPassword = "";//密码
        String sClass = "";//班级
        String sTeacher = "";//所属教师

        String md5Password = "";

        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            //对行判空
            if (row == null) {
                continue;
            }
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                System.out.println(row.getLastCellNum());
                //对列判空
                cell = row.getCell(j);
                System.out.println(cell);
                if (null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }
                switch (j) {
                    case 0:
                        String tId = this.getCellValue(cell);
                        sId = Integer.parseInt(tId);
                        break;
                    case 1:
                        sName = this.getCellValue(cell);
                        break;
                    case 2:
                        sGender = this.getCellValue(cell);
                        break;
                    case 3:
                        sPassword = this.getCellValue(cell);
                        MessageDigest m = MessageDigest.getInstance("MD5");
                        m.update(sPassword.getBytes("UTF-8"));
                        byte[] digest = m.digest();
                        md5Password = DigestUtil.convertToHexString(digest);
                        break;
                    case 4:
                        sClass = this.getCellValue(cell);
                        break;
                    case 5:
                        sTeacher = this.getCellValue(cell);
                        break;

                }

            }
            Student student = new Student(sId, sName, sGender, md5Password, sClass, sTeacher);
            list.add(student);
        }

        System.out.println("--->excel数据:StudentTable执行成功！");
        return list;
    }


    //从Excel表中获取选择题库表
    public List<Choice> getChoiceBankByExcel(String sFile, FileInputStream in) throws Exception {
        //list对象
        List<Choice> list = new ArrayList<Choice>();
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //创建excel表对象
        Workbook workbook = this.getWorkbook(sFile, in);
        //创建工作表对象
        sheet = workbook.getSheetAt(0);

        String topic = "";//题目
        String choiceA = "";//A选项
        String choiceB = "";//B选项
        String choiceC = "";//C选项
        String choiceD = "";//D选项
        String chapter = "";//所属章节
        String choiceRight = "";//正确答案
        String cLevel = "";//c等级
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            //对行判空
            if (row == null) {
                continue;
            }
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                //对列判空
                cell = row.getCell(j);
                if (null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }

                switch (j) {
                    case 0:
                        topic = this.getCellValue(cell);
                        break;
                    case 1:
                        choiceA = this.getCellValue(cell);
                        break;
                    case 2:
                        choiceB = this.getCellValue(cell);
                        break;
                    case 3:
                        choiceC = this.getCellValue(cell);
                        break;
                    case 4:
                        choiceD = this.getCellValue(cell);
                        break;
                    case 5:
                        chapter = this.getCellValue(cell);
                        break;
                    case 6:
                        choiceRight = this.getCellValue(cell);
                        break;
                    case 7:
                        cLevel = this.getCellValue(cell);
                }
            }
            if (!StringUtils.isNullOrEmpty(topic)) {
                list.add(new Choice(null, topic, choiceA, choiceB, choiceC, choiceD, chapter, choiceRight, cLevel));
            }
        }
        System.out.println("--->excel数据:ChoiceTable执行成功！");
        return list;
    }


    //从Excel表中获取填空题库表
    public List<GapFilling> getGapFillingBankByExcel(String sFile, FileInputStream in) throws Exception {
        //list对象
        List<GapFilling> list = new ArrayList<GapFilling>();
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //创建excel表对象
        Workbook workbook = this.getWorkbook(sFile, in);
        //创建工作表对象
        sheet = workbook.getSheetAt(0);

        String topic = "";//题目
        String answerRight1 = "";//正确答案
        String answerRight2 = "";//正确答案
        String answerRight3 = "";//正确答案
        String answerRight4 = "";//正确答案
        String chapter = "";//所属章节
        String cLevel = "";//c等级
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            //对行判空
            if (row == null) {
                continue;
            }

            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {

                //对列判空
                cell = row.getCell(j);
                if (null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }

                switch (j) {
                    case 0:
                        topic = this.getCellValue(cell);
                        break;
                    case 1:
                        answerRight1 = this.getCellValue(cell);
                        break;
                    case 2:
                        answerRight2 = this.getCellValue(cell);
                        break;
                    case 3:
                        answerRight3 = this.getCellValue(cell);
                        break;
                    case 4:
                        answerRight4 = this.getCellValue(cell);
                        break;
                    case 5:
                        chapter = this.getCellValue(cell);
                        break;
                    case 6:
                        cLevel = this.getCellValue(cell);
                }
            }
            if (!StringUtils.isNullOrEmpty(topic)) {
                list.add(new GapFilling(null, topic, answerRight1, answerRight2, answerRight3, answerRight4, chapter, cLevel));
            }
        }
        System.out.println("--->excel数据:GapFillingTable执行成功！");
        return list;
    }


    //从Excel表中获取判断题库表
    public List<Judge> getJudgeBankByExcel(String sFile, FileInputStream in) throws Exception {
        //list对象
        List<Judge> list = new ArrayList<Judge>();
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //创建excel表对象
        Workbook workbook = this.getWorkbook(sFile, in);
        //创建工作表对象
        sheet = workbook.getSheetAt(0);


        String topic = "";
        String answerRight = "";
        String chapter = "";

        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            //对行判空
            if (row == null) {
                continue;
            }
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                //对列判空
                cell = row.getCell(j);
                if (null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }

                switch (j) {
                    case 0:
                        topic = this.getCellValue(cell);
                        break;
                    case 1:
                        answerRight = this.getCellValue(cell);
                        break;
                    case 2:
                        chapter = this.getCellValue(cell);
                        break;
                }
            }
            if (!StringUtils.isNullOrEmpty(topic)) {
                list.add(new Judge(null, topic, answerRight, chapter));
            }
        }
        System.out.println("--->excel数据:JudgeTable执行成功！");
        return list;
    }


    //从Excel表中获取编程题库表
    public List<Programme> getProgrammeBankByExcel(String sFile, FileInputStream in) throws Exception {
        //list对象
        List<Programme> list = new ArrayList<Programme>();
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //创建excel表对象
        Workbook workbook = this.getWorkbook(sFile, in);
        //创建工作表对象
        sheet = workbook.getSheetAt(0);


        String topic = "";
        String answer1 = "";
        String answer2 = "";
        String answer3 = "";
        String chapter = "";
        String cLevel = "";//c等级
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            //对行判空
            if (row == null) {
                continue;
            }
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                //对列判空
                cell = row.getCell(j);
                if (null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }
                switch (j) {
                    case 0:
                        topic = this.getCellValue(cell);
                        break;
                    case 1:
                        answer1 = this.getCellValue(cell);
                        break;
                    case 2:
                        answer2 = this.getCellValue(cell);
                        break;
                    case 3:
                        answer3 = this.getCellValue(cell);
                        break;
                    case 4:
                        chapter = this.getCellValue(cell);
                        break;
                    case 5:
                        cLevel = this.getCellValue(cell);
                }
            }
            if (!StringUtils.isNullOrEmpty(topic)) {
                list.add(new Programme(null, topic, answer1, answer2, answer3, chapter, cLevel));
            }
        }
        System.out.println("--->excel数据:ProgrammeTable执行成功！");
        return list;
    }

    //从Excel表中获取自主命题
    public IndePropo getIndePropoByExcel(String sFile, FileInputStream in) throws Exception {
        IndePropo indepropo = null;
        Map<String, Choice> mapChoice = null;
        Map<String,GapFilling> mapGapFilling = null;
        Map<String,Judge> mapJudge = null;
        Map<String,Programme> mapProgramme =null;

        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //创建excel表对象
        Workbook workbook = this.getWorkbook(sFile, in);
        //创建工作表对象
        sheet = workbook.getSheetAt(0);


        int i = sheet.getLastRowNum();
        row = sheet.getRow(i);
        //对行判空
        if (row == null) {
            return new IndePropo();
        }
        String indeName = this.getCellValue(row.getCell(0));//获取命名

        for (int j = row.getFirstCellNum() + 1; j < row.getLastCellNum(); ) {

            if (j < 73) {   //小于73执行选择题循环
                mapChoice = new HashMap<>();
                for (int k = 1; ; k++) {
                    if (k==13){
                        break;
                    }
                    Choice indeChoice = new Choice();
                    indeChoice.setIndepropoName(indeName);
                    indeChoice.setTopic(this.getCellValue(row.getCell(j++)));

                    indeChoice.setChoiceA(this.getCellValue(row.getCell(j++)));
                    indeChoice.setChoiceB(this.getCellValue(row.getCell(j++)));
                    indeChoice.setChoiceC(this.getCellValue(row.getCell(j++)));
                    indeChoice.setChoiceD(this.getCellValue(row.getCell(j++)));
                    indeChoice.setChoiceRight(this.getCellValue(row.getCell(j++)));
                    System.out.println(indeChoice);
                    mapChoice.put("indeChoice" + k, indeChoice);
                }

            }

            if (j < 114) {//小于113执行填空题循环
                mapGapFilling = new HashMap<>();
                for (int k=1;;k++){
                    if (k==9){
                        break;
                    }
                    GapFilling indeGapFilling = new GapFilling();
                    indeGapFilling.setIndepropoName(indeName);
                    indeGapFilling.setTopic(this.getCellValue(row.getCell(j++)));
                    indeGapFilling.setAnswerRight1(this.getCellValue(row.getCell(j++)));
                    if (null == row.getCell(j) || row.getCell(j).getCellType() == Cell.CELL_TYPE_BLANK) {
                        indeGapFilling.setAnswerRight2("");
                        indeGapFilling.setAnswerRight3("");
                        indeGapFilling.setAnswerRight4("");
                        System.out.println(indeGapFilling);
                        mapGapFilling.put("gapfilling"+k,indeGapFilling);
                        j+=3;
                        continue;
                    }//判断下一个值是否为空，若为空则j+=3;
                    indeGapFilling.setAnswerRight2(this.getCellValue(row.getCell(j++)));
                    if (null == row.getCell(j) || row.getCell(j).getCellType() == Cell.CELL_TYPE_BLANK) {
                        indeGapFilling.setAnswerRight3("");
                        indeGapFilling.setAnswerRight4("");
                        System.out.println(indeGapFilling);
                        mapGapFilling.put("gapfilling"+k,indeGapFilling);
                        j+=2;
                        continue;
                    }
                    indeGapFilling.setAnswerRight3(this.getCellValue(row.getCell(j++)));
                    if (null == row.getCell(j) || row.getCell(j).getCellType() == Cell.CELL_TYPE_BLANK) {
                        indeGapFilling.setAnswerRight4("");
                        System.out.println(indeGapFilling);
                        mapGapFilling.put("gapfilling"+k,indeGapFilling);
                        j+=1;
                        continue;
                    }
                    indeGapFilling.setAnswerRight4(this.getCellValue(row.getCell(j++)));
                    System.out.println(indeGapFilling);
                    mapGapFilling.put("gapfilling"+k,indeGapFilling);
                }
            }

            if (j < 139) {//小于130执行判断题循环
                mapJudge = new HashMap<>();
                for (int k=1;;k++){
                    if (k==13){
                        break;
                    }
                    Judge indeJudge = new Judge();
                    indeJudge.setIndepropoName(indeName);
                    indeJudge.setTopic(this.getCellValue(row.getCell(j++)));
                    indeJudge.setAnswerRight(this.getCellValue(row.getCell(j++)));
                    System.out.println(indeJudge);
                    mapJudge.put("judge"+k,indeJudge);
                }
            }

            //剩余执行程序填空题循环
            mapProgramme = new HashMap<>();
            for (int k=1;;k++){
                if (k==4){
                    break;
                }
                Programme indeProgramme = new Programme();
                indeProgramme.setIndepropoName(indeName);
                indeProgramme.setTopic(this.getCellValue(row.getCell(j++)));
                indeProgramme.setAnswer1(this.getCellValue(row.getCell(j++)));
                indeProgramme.setAnswer2(this.getCellValue(row.getCell(j++)));
                indeProgramme.setAnswer3(this.getCellValue(row.getCell(j++)));
                System.out.println(indeProgramme);
                mapProgramme.put("programme"+k,indeProgramme);
            }
            break;

        }
        indepropo = new IndePropo(mapChoice,mapGapFilling,mapJudge,mapProgramme);
        System.out.println(indepropo);
        return indepropo;
    }
}



