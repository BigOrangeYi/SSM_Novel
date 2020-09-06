package com.ay.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class CheckCodeController {
    @Resource
    private ObjectMapper mapper;
    private char[] codeSequence = {'A', '1', 'B', 'C', '2', 'D', '3', 'E', '4', 'F', '5', 'G', '6', 'H', '7', 'I', '8', 'J',
            'K', '9', 'L', '1', 'M', '2', 'N', 'P', '3', 'Q', '4', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    @RequestMapping("/CheckCode.do")
    @ResponseBody
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        int width = 200;
        int height = 50;
        Random random = new Random();
        //设置response头信息
        //禁止缓存

        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(this.getColor(200, 250));
        g.setFont(new Font("Times New Roman", 0, 35));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for (int i = 0; i < 40; i++) {
            g.setColor(this.getColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            strCode = strCode + rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 65, 35);
        }
        //将字符保存到session中用于前端的验证
        session.setAttribute("C", strCode);
        System.out.println(session.getAttribute("C"));
        g.dispose();

        ImageIO.write(image, "JPG", response.getOutputStream());
        response.getOutputStream().flush();
    }

    public Color getColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    @RequestMapping("/CheckInputCode.do")
    @ResponseBody
    public void CheckInputCode(@RequestBody Map mapp,
                               HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {

        if (session.getAttribute("C").toString().equals(mapp.get("inputcode")) == false) {
            Map map = new HashMap();
            map.put("tip", "验证码错误");

            mapper.writeValue(response.getWriter(), map);
        } else {
            Map map = new HashMap();
            map.put("tip", "");

            mapper.writeValue(response.getWriter(), map);

        }

    }
}
