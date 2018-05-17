package wcy.godinsec.wcy_dandan.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Auther：杨玉安 on 2017/11/10 17:28
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DimenTool {

    public static void gen() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        File file = new File("D:/TextSpaces/Wcy_dandan/app/src/main/res/values-dpi/dimens.xml");

        BufferedReader reader = null;
        StringBuilder mDpi = new StringBuilder();
        StringBuilder hDpi = new StringBuilder();
        StringBuilder xhDpi = new StringBuilder();
        StringBuilder xxhDpi = new StringBuilder();
        StringBuilder xxxhDpi = new StringBuilder();


        try {

            System.out.println("生成不同分辨率：");

            reader = new BufferedReader(new FileReader(file));

            String tempString;

            int line = 1;

            // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {


                if (tempString.contains("</dimen>")) {

                    //tempString = tempString.replaceAll(" ", "");

                    String start = tempString.substring(0, tempString.indexOf(">") + 1);

                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2));

                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。
//                    mDpi.append(start).append( num * 0.75).append(end).append("\r\n");

                    hDpi.append(start).append(num * 0.75).append(end).append("\r\n");

                    xhDpi.append(start).append(num * 1).append(end).append("\r\n");

                    xxhDpi.append(start).append(num * 1.5).append(end).append("\r\n");

                    xxxhDpi.append(start).append(num * 2).append(end).append("\r\n");

                } else {
                    mDpi.append(tempString).append("");

                    hDpi.append(tempString).append("");

                    xhDpi.append(tempString).append("");

                    xxhDpi.append(tempString).append("");

                    xxxhDpi.append(tempString).append("");
                }

                line++;

            }

            reader.close();

            System.out.println(mDpi);

            System.out.println(hDpi);

            System.out.println(xhDpi);

            System.out.println(xxhDpi);

            System.out.println(xxxhDpi);

            String mdpifile = "D:/TextSpaces/Wcy_dandan/app/src/main/res/values-hdpi/dimens.xml";

            String hdpifile = "D:/TextSpaces/Wcy_dandan/app/src/main/res/values-hdpi/dimens.xml";

            String xhdpifile = "D:/TextSpaces/Wcy_dandan/app/src/main/res/values-xhdpi/dimens.xml";

            String xxhdpifile = "D:/TextSpaces/Wcy_dandan/app/src/main/res/values-xxhdpi/dimens.xml";

            String xxxhdpifile = "D:/TextSpaces/Wcy_dandan/app/src/main/res/values-xxxhdpi/dimens.xml";

            //将新的内容，写入到指定的文件中去
//            writeFile(mdpifile, mDpi.toString());

            writeFile(hdpifile, hDpi.toString());

            writeFile(xhdpifile, xhDpi.toString());

            writeFile(xxhdpifile, xxhDpi.toString());

            writeFile(xxxhdpifile, xxxhDpi.toString());
        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (reader != null) {

                try {

                    reader.close();

                } catch (IOException e1) {

                    e1.printStackTrace();

                }

            }

        }

    }


    /**
     * 写入方法
     */

    public static void writeFile(String file, String text) {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            out.println(text);

        } catch (IOException e) {

            e.printStackTrace();

        }


        out.close();

    }

    public static void main(String[] args) {

        gen();

    }

}