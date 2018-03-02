package com.jkgroup.kingkaid.service.shiro;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jkgroup.kingkaid.web.index.LoginController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class RandomValidateCode {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";// 放到session中的key
    public static final String VCODEKEY = "VCODEVALIDATEKEY";
    public static final String VCODEMMMKEY = "VCODEMMMVALIDATEKEY";
    private Random random = new Random();
    //	private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串
    private String randString = "0123456789";// 随机产生的字符串

    private int width = 95;// 图片宽
    private int height = 26;// 图片高
    private int lineSize = 40;// 干扰线数量
    private int stringNum = 4;// 随机产生字符数量

    private Integer REGISTER_VERIFICATION_CODE_COUNT = 7;
    private Integer VERIFICATION_CODE_RED_COUNT = 4;

    private Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 16);
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - 16);
        int g = fc + random.nextInt(bc - 14);
        int b = fc + random.nextInt(fc - 18);
        return new Color(r, g, b);
    }

    public BufferedImage getBufferImage() {
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = drowString(g, randomString, i);
        }
        return image;
    }

    /**
     * 生成注册随机图片
     */
    public void getLoginRandcode(HttpServletRequest request,
                                 HttpServletResponse response) {
        getRandcode(response, RANDOMCODEKEY);
    }

    /**
     * 生成7选4验证码
     */
    public void getLoginVerificationCode(HttpServletRequest request,
                                         HttpServletResponse response) {
        getVerificationCode(response, RANDOMCODEKEY);
    }

    /**
     * 生成注册随机图片
     */
    public void getVCodeRandcode(HttpServletRequest request,
                                 HttpServletResponse response) {
        getRandcode(response, VCODEKEY);
    }

    /**
     * 生成7选4验证码
     */
    public void getRegVerificationCode(HttpServletRequest request,
                                       HttpServletResponse response) {
        getVerificationCode(response, VCODEKEY);
    }

    /**
     * 生成找回密码随机图片
     */
    public void getVCodeMRandcode(HttpServletRequest request,
                                  HttpServletResponse response) {
        getRandcode(response, VCODEMMMKEY);
    }

    /**
     * 生成随机图片
     */
    private void getRandcode(HttpServletResponse response, String sessionKey) {
        Session session = SecurityUtils.getSubject().getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(getRandColor(60, 120));
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = drowString(g, randomString, i);
        }
        session.removeAttribute(sessionKey);
        session.setAttribute(sessionKey, randomString);
        log.info("in generation: session id is {}, vcode is {}",
                session.getId(), randomString);
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());// 将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成7选4验证码图片
     * @param response
     * @param sessionKey
     */
    private void getVerificationCode(HttpServletResponse response, String sessionKey) {
        Session session = SecurityUtils.getSubject().getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(getRandColor(60, 120));

        // 绘制随机字符
        int[] verifictionCodePosition = randomCommon(1, REGISTER_VERIFICATION_CODE_COUNT, VERIFICATION_CODE_RED_COUNT);

        String randomString = "";
        String redString = "";
        Pos:
        for (int i = 1; i <= REGISTER_VERIFICATION_CODE_COUNT; i++) {
            for (int j = 0; j < verifictionCodePosition.length; j++) {
                if (verifictionCodePosition[j] == i) {
                    String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
                    drowRedString(g, rand, i);
                    redString += rand;
                    randomString += rand;
                    continue Pos;
                }
            }
            randomString = drowString(g, randomString, i);
        }
        session.removeAttribute(sessionKey);
        session.setAttribute(sessionKey, redString);
        log.info("in generation: session id is {}, vcode is {}", session.getId(), redString);
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    /*
     * 绘制字符串
     */
    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(20), random.nextInt(40), random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 5 + 11 * (i - 1), 12);
        return randomString;
    }

    /*
     * 绘制红色字符串
     */
    private void drowRedString(Graphics g, String rand, int i) {
        g.setFont(getFont());
        g.setColor(new Color(255, 0, 0));
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 5 + 11 * (i - 1), 16);
    }

    /*
     * 绘制干扰线
     */
    private void drowLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(50);
        int yl = random.nextInt(150);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /*
     * 获取随机的字符
     */
    public String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }

    // 二维码 start
    public void getCodezxing(HttpServletRequest request,
                             HttpServletResponse response) {
        String text;
        String url = request.getParameter("url");
        if (url == null || url.equals("")) {
            StringBuffer path = request.getRequestURL();
            String pathtemp = null;
            if (path.indexOf("kkd") != -1) {
                pathtemp = path.substring(0, path.indexOf("kkd"));
            }
            ;
            text = pathtemp.concat("kkd/website/mobdown.html");
        } else {
            text = url;
        }
        log.info("in generation: text  is {}", text);
        int width = Integer.parseInt(request.getParameter("width"));
        int height = Integer.parseInt(request.getParameter("height"));

        /**
         * 设置二维码的参数
         */
        HashMap hints = new HashMap();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix;

        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,
                    width, height, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            ImageIO.write(image, "JPEG", response.getOutputStream());// 将内存中的图片通过流动形式输出到客户端
        } catch (WriterException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}
