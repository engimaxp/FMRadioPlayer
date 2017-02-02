package FMRadio;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by Wang on 2017/1/15.
 * A FMRadio Player Can Choose Songs From the DBFMRadio
 * 1.It has a history list,saved on local hard drive
 * 2.It can target favorite songs,list saved on local hard drive
 * 3.favorite songs will be download to local hard drive
 * 4.you can play favorite songs from local hard drive
 * 5.you can play\pause\jump songs you are playing
 */
public class FMRadioPlayer extends JFrame implements Observer{
    private ImageButton btnPlayOrStop;
    private ImageButton btnPause;
    private ImageButton btnNext;
    private ImageButton btnLike;
    private ImageButton btnRadioOrLocal;
    private JLabel statusText;
    private JPanel row1 =  new JPanel();
    private JPanel row2 =  new JPanel();
    private JPanel row3 =  new JPanel();
    private JLabel recordCover;

	private static final long serialVersionUID = 5474637165603916178L;
	//init controls
    public void init(){
        Container container=getContentPane();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPlayOrStop = new ImageButton(getImageIcon("play.png",50,50));
        btnPause = new ImageButton(getImageIcon("pause.png",50,50));
        btnNext = new ImageButton(getImageIcon("next.png",50,50));
        btnLike = new ImageButton(getImageIcon("Favorite.png",50,50));
        btnRadioOrLocal = new ImageButton(getImageIcon("Globe.png",50,50));
        recordCover = new JLabel(getImageIcon("Music_Record.png",250,250));
        row1.add(recordCover);
        container.add(row1);
        row2.add(btnPlayOrStop);
        row2.add(btnPause);
        row2.add(btnNext);
        row2.add(btnLike);
        row2.add(btnRadioOrLocal);
        container.add(row2);
        statusText = new JLabel();
        row3.add(statusText);
        container.add(row3);
        setTitle("FMRadio");
        setSize(400,400);
        setVisible(true);
        
        bindControlsActionListener();        
        
        //设置容器的关闭方式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private boolean isPlaying = false;
    private boolean isPaused = true;
    private void bindControlsActionListener() {
        btnPlayOrStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assert(player != null);
                if(isPlaying){
                    player.stop();
                    isPlaying = false;
                    statusText.setText("Stopped Playing");
                }else{
                    player.start();
                    isPlaying = true;
                    statusText.setText("Start Playing");
                }
            }
        });
    }

    //reading local document
    private AudioPlayer player;
    private UrlProvider provider= new UrlProvider();
    private MusicRadio musicRadio = new MusicRadio();
    public void start() {
        Music song = getNextSong();
        player = new AudioPlayer(provider);
        player.registerObserver(this);
        player.start();
    }

    private Music getNextSong() {
        Music song = new Music();
        try {
            song = musicRadio.getASong();
        } catch (Exception e) {
            statusText.setText(e.getMessage());
        }
        try {
            provider.addNextUrl(new URL(song.getMp3Url()));
        } catch (MalformedURLException e) {
            statusText.setText("songs address is unreachable");
        }
        return song;
    }

    private ImageIcon getImageIcon(String path, int width, int height) {
        if (width == 0 || height == 0) {
            return new ImageIcon(this.getClass().getResource(path));
        }
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
        icon.setImage(icon.getImage().getScaledInstance(width, height,
                Image.SCALE_DEFAULT));
        return icon;
    }
    public static void main(String[] args){
        FMRadioPlayer f = new FMRadioPlayer();
        f.init();
        f.start();
    }

    @Override
    public void update() {
        if(getNextSong()==null)
            statusText.setText("No More Songs");
    }

    public class ImageButton extends JButton {

        public ImageButton(ImageIcon icon){
            setSize(icon.getImage().getWidth(null),
                    icon.getImage().getHeight(null));
            setIcon(icon);
            setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0
            setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
            setBorder(new EtchedBorder());//除去边框
            setText(null);//除去按钮的默认名称
            setFocusPainted(false);//除去焦点的框
            setContentAreaFilled(false);//除去默认的背景填充
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setBorder(new BevelBorder(BevelBorder.RAISED));
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setBorder(new BevelBorder(BevelBorder.LOWERED));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBorder(new EtchedBorder());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(new BevelBorder(BevelBorder.RAISED));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(new EtchedBorder());
                }
            });
        }
    }
}
