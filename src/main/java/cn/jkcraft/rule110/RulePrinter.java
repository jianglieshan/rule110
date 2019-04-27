package cn.jkcraft.rule110;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RulePrinter {
    public static void main(String[] args) {

        //BufferedImage image = new BufferedImage(10, 80, BufferedImage.TYPE_4BYTE_ABGR);


        //JFrame window1 = new JFrame("撤销窗口"); //创建标题名
        JFrame window2 = new JFrame("退出程序");
        //window1.setBounds(0,0,400,200);// 显示X轴位置,显示Y轴位置 ,宽,长
        window2.setBounds(400,0,500,500);
        //window1.setVisible(true); // 窗口默认是不可见的
        //window1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //设置单击窗体右上角关闭图标后，程序会做出怎样的处理。
        MyPanel myPanel = new MyPanel(500,500);
        window2.add(myPanel);
        window2.setVisible(true);
        window2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myPanel.shift();
    }
}

class MyPanel extends JPanel {
    int width;
    int height;
    private JLabel label;
    Rule110 rule110;

    public MyPanel(int width,int height) {
        rule110 = new Rule110(100,100,Rule110.standar());
        rule110.start(200);
        this.setLayout(new GridLayout());
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(this.width, this.height));


        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        g.dispose();
        label = new JLabel(new ImageIcon(bi));
        this.add(label, JLabel.CENTER);
    }

    void shift(){

        int dw = width/rule110.getColumn();
        int dh = height/rule110.getRow();

        while (true){

            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();

            rule110.iteratorOnce();

            for (int i = 0; i < rule110.getRow(); i++) {
                for (int j = 0; j < rule110.getColumn(); j++) {
                    if(rule110.getMatrix()[i][j] == 0){
                        g.setColor(Color.black);
                    }
                    else if(rule110.getMatrix()[i][j] == 1){
                        g.setColor(Color.white);
                    }
                    else{
                        g.setColor(Color.red);
                    }
                    g.fillRect(i*dw,j*dh,dw,dh);
                }
            }
            g.dispose();
            label.setIcon(new ImageIcon(bi));

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
