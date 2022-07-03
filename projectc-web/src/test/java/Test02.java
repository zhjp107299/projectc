
import com.seven.dao.ExcelInputDBMapper;
import com.seven.dao.QbScoreMapper;

import com.seven.pojo.*;
import com.seven.utils.AnalysisTextToMap;
import com.seven.utils.FetchInfoFromExcel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Test02 {
    @Test
    public void timeTest(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(now.plusMonths(1));
        System.out.println(now.plusMonths(3));
        System.out.println(now.plusMonths(6));
        System.out.println(now.plusMonths(12));
        List<Float> dataList = new ArrayList<>();
        Float[] floats = dataList.toArray(new Float[dataList.size()]);
    }

    @Test
    public void utilTest(){
        List<String> strings = AnalysisTextToMap.analysisTextToMapIName("seq=1,name=3570-test1,score=90;seq=2,name=3570-test2,score=91;");
        Map<String, Integer> stringIntegerMap = AnalysisTextToMap.analysisTextToMapI("seq=1,name=3570-test1,score=90;seq=2,name=3570-test2,score=91;");
        System.out.println(stringIntegerMap);
        System.out.println(strings);
    }

    @Test
    public void indeTest() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ExcelInputDBMapper excelInputDBMapper = applicationContext.getBean("excelInputDBMapper", ExcelInputDBMapper.class);

        FetchInfoFromExcel fetchInfoFromExcel = new FetchInfoFromExcel();
        FileInputStream in = new FileInputStream(new File("C:\\Users\\asus\\Desktop\\test2.xls"));
        IndePropo indePropoByExcel = fetchInfoFromExcel.getIndePropoByExcel("text.xls", in);
        List<Choice> indeChoiceList = new ArrayList<>();
        Set<Map.Entry<String, Choice>> entries = indePropoByExcel.getMapChoice().entrySet();
        for (Map.Entry<String, Choice> entry : entries) {
            indeChoiceList.add(entry.getValue());
        }
        excelInputDBMapper.indeChoiceInputDB(indeChoiceList);
        System.out.println(111);

    }

    @Test
    public void excelTest() throws IOException {
        //list对象
        List<Student> list = new ArrayList<Student>();
        Sheet sheet = null;
        Row row = null;
        Cell cell =null;
        //创建excel表对象
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream("C:\\Users\\asus\\Desktop\\test.xls"));
        HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
        //Workbook rwb = Workbook.getWorkbook(new File(file));
        //创建工作表对象
        sheet = workbook.getSheetAt(0);
        System.out.println(workbook.getNumberOfSheets());

        int rows = sheet.getLastRowNum();
        Row row0 = sheet.getRow(0);
        Row row1 = sheet.getRow(1);
        Row row2 = sheet.getRow(2);
        Row row3 = sheet.getRow(3);
        System.out.println(row0.getLastCellNum());
        System.out.println(row1.getLastCellNum());
        System.out.println(row2.getLastCellNum());
        System.out.println(row3.getLastCellNum());
        System.out.println(rows);



        System.out.println(row0.getLastCellNum());
        System.out.println(row0.getFirstCellNum());

        System.out.println(row1.getLastCellNum());
        Integer sId = 0;//学号
        String sName = "";//姓名
        String sGender = "";//性别
        String sPassword = "";//密码
        String sClass = "";//班级
        String sTeacher = "";//所属教师
        for (int i=sheet.getFirstRowNum()+1;i<sheet.getLastRowNum();i++){
            row = sheet.getRow(i);
            for (int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                System.out.println(j);
                cell = row.getCell(j);
                switch (j){
                    case 0:
                        sId = (int)cell.getNumericCellValue();
                        System.out.println(sId);
                        break;
                    case 1:
                        sName = cell.getStringCellValue();
                        System.out.println(sName);
                        break;
                    case 2:
                         sGender = cell.getStringCellValue();
                        System.out.println(sGender);
                        break;
                    case 3:
                        sPassword = cell.getRichStringCellValue().toString();
                        System.out.println(sPassword);
                        break;
                    case 4:
                        sClass = cell.getRichStringCellValue().toString();
                        System.out.println(sClass);
                        break;
                    case 5:
                        sTeacher = cell.getStringCellValue();
                        System.out.println(sTeacher);
                        break;
                }

            }
            Student student = new Student(sId, sName, sGender, sPassword, sClass, sTeacher, new Date());
            list.add(student);
        }
        for (Student student : list) {
            System.out.println(student);
        }


    }

    @Test
    public void getTextData(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        QbScoreMapper qbScoreMapper = applicationContext.getBean("qbScoreMapper", QbScoreMapper.class);
        List<String> list = new ArrayList<>();

        QbChapter chapterScore = qbScoreMapper.getChapterScore(208180214);
        System.out.println(chapterScore);

        String scoreChapter1 = chapterScore.getScoreChapter1();
        System.out.println(scoreChapter1);
        String[] split1 = scoreChapter1.split(";");
        String[] split2 = split1[split1.length - 1].split(",");
        System.out.println(split2[0].substring(4));

       /* for (int i=0;i<split1.length;i++){
            list.add(split1[i]);
        }
        System.out.println(list);*/

        /*Map<String, Integer> stringIntegerMap = AnalysisTextToMap.analysisTextToMap(scoreChapter1);
        Set<Map.Entry<String, Integer>> entries = stringIntegerMap.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry);
        }*/
    }
}
