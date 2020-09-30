package com.mt.government.utils;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MyExcelUtil {


    /**
     * 传入的两个数组长度一致(必须)    数组长度决定了导出的列数
     *
     * @param response
     * @param list         需要导出的对象list
     * @param fileName     导出的文件名称  xx.xls
     * @param headArr      头部名称数组  实体类的属性值(英文)
     * @param headArrAlias 实体类的属性别名(中文)
     * @throws Exception
     */
    public static void getExcel(HttpServletResponse response, List<?> list, String fileName, String[] headArr, String[] headArrAlias) throws Exception {

        if (!(headArr.length == headArrAlias.length)) { //  如果长度不相同
            throw new Exception();
        }

        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 此处的headArr和headArrAlias的长度需要一致，否则报错
        for (int i = 0; i < headArr.length; i++) {
            writer.addHeaderAlias(headArr[i], headArrAlias[i]);
        }
        writer.setOnlyAlias(true);
        writer.write(list);
        writer.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename= " + MyExcelUtil.toUtf8String(fileName));//默认Excel名称

        writer.flush(response.getOutputStream());
        writer.close();

    }


    /**
     * 导出  excel名称  英文转中文
     *
     * @param s
     * @return
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

}
