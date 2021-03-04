package com.mythsun.tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片对比工具类
 */
public class ImgDiffUtil {

    /**
     * 图片转字符串
     *
     * @param fileName   图片路径
     * @param formatName 图片类型，例如：png、jpg
     *                   默认png
     * @return
     * @throws IOException
     */
    public static String imgToString(String fileName, String formatName) throws IOException {
        if (formatName == null || "".equals(formatName)) {
            formatName = "png";
        }
        BufferedImage bi;
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        bi = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bi, formatName, bos);
        return bos.toString();
    }

    /**
     * 字符串转图片
     *
     * @param saveFilePath 转换成的图片保存的路径，包含图片名称及尾缀
     * @param formatName   图片类型，例如：png、jpg
     *                     默认png
     * @param string
     * @throws IOException
     */
    public static void stringToImg(String saveFilePath, String formatName, String string) throws IOException {
        if (formatName == null || "".equals(formatName)) {
            formatName = "png";
        }
        byte[] bytes = string.getBytes();
        File file = new File(saveFilePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(bis);
        ImageIO.write(bi, formatName, file);
    }

    /**
     * 获得文件的固定区域的像素值的数组
     *
     * @param fileName 文件路径
     * @param startX   起始x坐标 负数将被修正为文件的最小x坐标；超过文件最大x坐标抛出异常
     * @param startY   起始y坐标 负数将被修正为文件的最小y坐标；超过文件最大y坐标抛出异常
     * @param width    最大x坐标 超过文件最大x坐标将被修正为文件的最大x坐标
     * @param height   最大y坐标 超过文件最大y坐标将被修正为文件的最大y坐标
     * @return array[像素x坐标][像素y坐标]
     * @throws IOException
     */
    public static String[][] getPixelRgb(String fileName, int startX, int startY, int width, int height) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            new FileNotFoundException();
        }
        BufferedImage bi = ImageIO.read(file);
        int maxWidth = bi.getWidth();
        if (width < 0 || width > maxWidth) {
            width = bi.getWidth();
        }
        int maxHeight = bi.getHeight();
        if (height < 0 || height > maxHeight) {
            height = bi.getHeight();
        }
        if (startX < 0) {
            startX = bi.getMinX();
        }
        if (startX > maxWidth) {
            new Exception("The start x-coordinate is out of bounds");
        }
        if (startY < 0) {
            startY = bi.getMinY();
        }
        if (startY > maxHeight) {
            new Exception("The start y-coordinate is out of bounds");
        }
        int[] rgb = new int[3];
        String[][] pixels = new String[width][height];
        for (int i = startX; i < width; i++) {
            for (int j = startY; j < height; j++) {
                int pixel = bi.getRGB(i, j);
                // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                pixels[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
                //System.out.println("i:" + i + "j:" + j + "[" + rgb[0] + "," + rgb[1] + "," + rgb[2] + "]");
            }
        }
        return pixels;
    }

    /**
     * 比较两个像素值数组，判断相同的比率
     *
     * @param str1
     * @param str2
     * @param compareLimit 相似比
     * @return 是否符合规定的相似比
     */
    public static boolean compareByPixel(String[][] str1, String[][] str2, int compareLimit) {
        //判断两图是否为按比例拉伸图，不是则判断两图不同
        if ((str1.length / str1[1].length) != (str2.length / str2[1].length)) {
            return false;
        }
        int width, height;
        width = str1.length < str2.length ? str1.length : str2.length;
        height = str1[1].length < str2[1].length ? str1[1].length : str2[1].length;
        double allPix = width * height;
        if (allPix == 0) {
            new Exception("The array is empty");
        }
        double count = 0;
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (str1[i][j].equals(str2[i][j])) {
                    count++;
                }
            }
        }
        if ((count / allPix) * 100 > compareLimit) {
            return true;
        }
        return false;
    }

    /**
     * 把A图片指定区域，替换到B图片对应区域
     *
     * @param fileName     文件路径（A图片）
     * @param saveFileName 替换后的文件路径（B图片）
     * @param formatName   图片类型，例如：png、jpg
     *                     默认png
     * @param startX       起始x坐标 负数将被修正为文件的最小x坐标；超过文件最大x坐标抛出异常
     * @param startY       起始y坐标 负数将被修正为文件的最小y坐标；超过文件最大y坐标抛出异常
     * @param width        最大x坐标 超过文件最大x坐标将被修正为文件的最大x坐标
     * @param height       最大y坐标 超过文件最大y坐标将被修正为文件的最大y坐标
     * @throws IOException
     */
    public static void replaceImg(String fileName, String saveFileName, String formatName, int startX, int startY, int width, int height) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (formatName == null || "".equals(formatName)) {
            formatName = "png";
        }
        BufferedImage biOld;
        biOld = ImageIO.read(file);
        int maxWidth = biOld.getWidth();
        if (width < 0 || width > maxWidth) {
            width = biOld.getWidth();
        }
        int maxHeight = biOld.getHeight();
        if (height < 0 || height > maxHeight) {
            height = biOld.getHeight();
        }
        if (startX < 0) {
            startX = biOld.getMinX();
        }
        if (startX > maxWidth) {
            new Exception("The start x-coordinate is out of bounds");
        }
        if (startY < 0) {
            startY = biOld.getMinY();
        }
        if (startY > maxHeight) {
            new Exception("The start y-coordinate is out of bounds");
        }
        byte[] bytes = imgToString(saveFileName, "png").getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage biNew = ImageIO.read(bis);
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                int pixel = biOld.getRGB(i, j);
                biNew.setRGB(i, j, pixel);
            }
        }
        ImageIO.write(biNew, formatName, file);
    }

}
