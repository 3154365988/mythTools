package com.mythsun.tools;

import java.io.*;
import java.util.regex.Pattern;

public class RegexUtil {

    /**
     * 对文件内容正则匹配
     * @param filePath 文件路径
     * @param regex 正则表达式
     * @return 所有符合的行
     * @throws IOException
     */
    public static String matchFileContent(String filePath, String regex) throws IOException {
        StringBuilder sb = new StringBuilder();
        File file = new File(filePath);
        if (!file.exists()){
            throw new FileNotFoundException();
        }
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine()) != null) {
            if (Pattern.compile(regex).matcher(line).find()) {
                sb.append(line);
                sb.append("\n");
            }
        }
        reader.close();
        br.close();
        return sb.toString().trim();
    }

}
