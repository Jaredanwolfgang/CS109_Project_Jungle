package view;

import listener.HoverListener;
import view.Frame.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color normalBackground;
    private Color hoverBackground;
    private Color labelledBackground;
    private boolean hovered;
    private boolean labelled;
    private boolean timermode;
    private final int cornerRadius;
    private HoverListener hoverListener;

    public CellComponent(Color normalBackground, Color hoverBackground, Color labelledBackground, Point location, int size) {
        setLayout(new GridLayout(1, 1));
        setLocation(location);
        setSize(size, size);
        this.normalBackground = normalBackground;
        this.hoverBackground = hoverBackground;
        this.labelledBackground = labelledBackground;
        this.cornerRadius = size / 4;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (ChessGameFrame.enabled && hoverListener != null) {
                    hoverListener.onHovered(CellComponent.this);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (ChessGameFrame.enabled && hoverListener != null) {
                    hoverListener.onExited(CellComponent.this);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(ChessGameFrame.enabled){
                    // Trigger the click event in ChessboardComponent
                    getParent().dispatchEvent(new MouseEvent(getParent(), e.getID(), e.getWhen(), e.getModifiersEx(), getLocation().x + e.getX(), getLocation().y + e.getY(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
                }
            }
        });
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public void setLabelled(boolean labelled) {
        this.labelled = labelled;
    }

    public void setTimerMode(boolean timermode) {
        this.timermode = timermode;
    }

    public void setHoverListener(HoverListener hoverListener) {
        this.hoverListener = hoverListener;
    }

    public void setBackground(Color normalBackground,Color hoverBackground,Color labelledBackground) {
        this.normalBackground = normalBackground;
        this.hoverBackground = hoverBackground;
        this.labelledBackground = labelledBackground;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;

        if (labelled) {
            g2d.setColor(labelledBackground);
        } else if (hovered) {
            g2d.setColor(hoverBackground);
        } else {
            g2d.setColor(normalBackground);
        }
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, this.getWidth() , this.getHeight(), cornerRadius, cornerRadius);
        g2d.fill(roundedRectangle);

        if (timermode) {
            Graphics2D shine = (Graphics2D) g;
            shine.setColor(new Color(0,0,0,128));
            RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Double(0, 0, this.getWidth() , this.getHeight(), cornerRadius, cornerRadius);
            shine.fill(roundRectangle2D);
        }
    }

}
