package com.yunwang.util.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

@Controller
@Action(value = "vcodeAction",
	results=@Result(name="success",type="stream",
				params={"encode","true","contentType","image/jpeg",
				"inputName","inputImage","bufferSize","512" })
)
public class VcodeAction extends BaseAction{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 2731041834871227056L;
	
	
	private ByteArrayInputStream inputImage;
	private static final Font VERIFICATION_FONT = new Font("Times New Roman", Font.PLAIN, 20);
	
	public String execute() throws IOException {		
		int width = 95, height = 32;
		//创建图象 
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics gra = image.getGraphics();		
		Random random = new Random();		
        //背景色
		gra.setColor(getRandColor(200, 250));
		gra.fillRect(0, 0, width, height);
        //字体色
		gra.setColor(Color.black);
		gra.setFont(VERIFICATION_FONT);
		//边框
		gra.setColor(new Color(0)); 
		gra.drawRect(0,0,width-1,height-1);
//		// 随机产生60条干扰线
		gra.setColor(getRandColor(120, 160));
		for (int i = 0; i < 60; i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(20);
			int yl = random.nextInt(20);
			gra.drawLine(x, y, x + xl, y + yl);
		}
		//可用字符(26个小写字母除去o,l,z,10个数字除去0,1,2)
		char c[] = {'0','1','2','3','4','5','6','7','8','9'};
		StringBuilder sRand = new StringBuilder();
		for (int i = 0; i < 4; i++){
			int index = random.nextInt(10);
			String rand = String.valueOf(c[index]);
			sRand.append(rand);
			gra.setFont(new Font("Courier New",Font.BOLD, 28));
			gra.setColor(new Color(random.nextInt(120), random.nextInt(120), random.nextInt(120)));
			gra.drawString(rand, 18 * i + 18, 25);
		}
		gra.dispose();

		sessionMap.put("vcode", sRand.toString());
        ByteArrayOutputStream output = new ByteArrayOutputStream();   
        ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);   
        ImageIO.write(image, "JPEG", imageOut);   
        imageOut.close();   
        inputImage = new ByteArrayInputStream(output.toByteArray());  
        return SUCCESS;
	}
	
	private Color getRandColor(int fc, int bc){
		Random random = new Random();
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public ByteArrayInputStream getInputImage() {
		return inputImage;
	}

}
