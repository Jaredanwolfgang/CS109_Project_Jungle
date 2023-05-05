package view.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.sound.sampled.FloatControl;

public class MusicPlayerFrame extends JFrame {
    private static int INIT_VOLUME = 80;//音乐播放器创建时的默认音量，也是音量滑动条的初始位置
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private static Clip clip;
    private static long clipTimePosition;
    private JSlider slider;
    private static int volume = INIT_VOLUME;
    private Frame frame;
    public MusicPlayerFrame(Frame frame) {
        //构造方法
        this.frame = frame;
        //创建按钮
        JPanel panel = new JPanel();
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        //播放按钮按下时
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("音乐播放器：播放按钮按下");
                playMusic();
            }
        });
        //暂停按钮按下时
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("音乐播放器：暂停按钮按下");
                if (clip != null && clip.isRunning()) {
                    //用clipTimePosition记录目前位置
                    clipTimePosition = clip.getMicrosecondPosition();
                    clip.stop();
                }
            }
        });
        //停止按钮按下时
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("音乐播放器：停止按钮按下");
                if (clip != null) {
                    clip.stop();
                    //将音乐位置改回起点
                    clipTimePosition = 0;
                    clip.setMicrosecondPosition(clipTimePosition);
                }
            }
        });

        //添加音量滑动条
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, INIT_VOLUME);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        //音量滑动条移动时
        slider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                //volume是滑轨的值，大小在0-100之间
                volume = source.getValue();
                System.out.println("音乐播放器：音量值改变为 " + volume + "%" );

                if(clip != null){
                    //修改音量
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float min = gainControl.getMinimum();
                    float max = gainControl.getMaximum();
                    float value = (max - min) * (float)volume / 100 + min;
                    gainControl.setValue(value);
                }
            }
        });

        //添加按钮和音量滑动条
        panel.add(slider);
        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(stopButton);
        add(panel);

        //设置音乐播放器本身Frame的属性   注意：创建时播放器默认为不可见
        setTitle("Music Player");
        setSize(500, 100);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//关闭操作由windowListener接管
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(false);

        //添加窗口监听
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                //当窗口右上角关闭按钮按下时，只隐藏窗口
                System.out.println("音乐播放器：隐藏");
                setVisible(false);
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        //音乐播放器被创建时就开始播放音乐
        playMusic();
    }

    public static void playMusic() {
        //如果已经在播放音乐执行此条
        if (clip != null && clip.isRunning()) {
            clipTimePosition = clip.getMicrosecondPosition();
            clip.stop();
        }

        try {
            //获取音乐文件
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Music\\music.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            //如果记录了暂停位置，则从该位置开始播放
            if (clipTimePosition > 0) {
                clip.setMicrosecondPosition(clipTimePosition);
            }

            //默认为循环播放
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            //音量大小更改
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float value = (max - min) * (float) volume / 100 + min;
            gainControl.setValue(value);

            //开始播放
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

}

