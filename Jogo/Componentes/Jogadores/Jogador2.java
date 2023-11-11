package Jogo.Componentes.Jogadores;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import Jogo.Componentes.Tiro;;

public class Jogador2 {

	private int x;
	private int y;
	private int dx;
	private int dy;
	private Image imagem;
	private int altura;
	private int largura;
	private boolean isVisivel;

	private List<Tiro> tiros;
	private boolean podeAtirar = true;
    private long tempoUltimoTiro = System.currentTimeMillis();
    private long intervaloTiros = 200;

	public Jogador2() {
		this.x = 100;
		this.y = 100;
		isVisivel = true;

		tiros = new ArrayList<Tiro>();
	}

	public void load() {
		ImageIcon referencia = new ImageIcon("assets//br-callister2.gif");
		imagem = referencia.getImage();
		altura = imagem.getHeight(null);
		largura = imagem.getWidth(null);
	}

	public void update() {
		x += dx;
		y += dy;
	}

	public void tiroSimples() {
		long tempoAtual = System.currentTimeMillis();
        if (tempoAtual - tempoUltimoTiro >= intervaloTiros) {
            this.tiros.add(new Tiro(x + largura, y + (altura / 2)));
            tempoUltimoTiro = tempoAtual;
        }
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, largura, altura);
	}

	public void keyPressed(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();
		if (codigo == KeyEvent.VK_ENTER && podeAtirar){
			tiroSimples();
			podeAtirar = false;
		}
		switch (codigo) {
			case KeyEvent.VK_UP:
				dy=-4;
				if (codigo == KeyEvent.VK_LEFT){
					dx=-4;
				} else if (codigo == KeyEvent.VK_RIGHT){
					dx=4;
				} else if (codigo == KeyEvent.VK_DOWN){
					dy=4;
				}
				break;
			
			case KeyEvent.VK_LEFT:
				dx=-4;
				if (codigo == KeyEvent.VK_UP){
					dy=-4;
				} else if (codigo == KeyEvent.VK_DOWN){
					dy=4;
				} else if(codigo == KeyEvent.VK_RIGHT) {
					dx=4;
				}
				break;

			case KeyEvent.VK_RIGHT:
				dx=4;
				if (codigo == KeyEvent.VK_UP){
					dy=-4;
				} else if (codigo == KeyEvent.VK_DOWN){
					dy=4;
				} else if(codigo == KeyEvent.VK_LEFT) {
					dx=-4;
				}
				break;

			case KeyEvent.VK_DOWN:
				dy=4;
				if (codigo == KeyEvent.VK_LEFT){
					dx=-4;
				} else if (codigo == KeyEvent.VK_RIGHT){
					dx=4;
				} else if(codigo == KeyEvent.VK_UP) {
					dy=-4;
				}
				break;
		}
		if (codigo == KeyEvent.VK_ENTER) {
            podeAtirar = true;
        }
	}

	public void keyRelease(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();

		switch (codigo) {
			case KeyEvent.VK_UP:
				dy=0;
				if (codigo == KeyEvent.VK_LEFT){
					dx=0;
				} else if (codigo == KeyEvent.VK_RIGHT){
					dx=0;
				} else if (codigo == KeyEvent.VK_DOWN){
					dy=0;
				}
				break;
			
			case KeyEvent.VK_LEFT:
				dx=0;
				if (codigo == KeyEvent.VK_UP){
					dy=0;
				} else if (codigo == KeyEvent.VK_DOWN){
					dy=0;
				} else if(codigo == KeyEvent.VK_RIGHT) {
					dx=0;
				}
				break;

			case KeyEvent.VK_RIGHT:
				dx=0;
				if (codigo == KeyEvent.VK_UP){
					dy=0;
				} else if (codigo == KeyEvent.VK_DOWN){
					dy=0;
				} else if(codigo == KeyEvent.VK_UP) {
					dx=0;
				}
				break;

			case KeyEvent.VK_DOWN:
				dy=0;
				if (codigo == KeyEvent.VK_LEFT){
					dx=0;
				} else if (codigo == KeyEvent.VK_RIGHT){
					dx=0;
				} else if(codigo == KeyEvent.VK_UP) {
					dy=0;
				}
				break;
		}
		if (codigo == KeyEvent.VK_ENTER) {
            podeAtirar = true;
        }
	}

	public List<Tiro> getTiros() {
		return tiros;
	}

	public boolean isVisivel() {
		return isVisivel;
	}

	public void setVisivel(boolean isVisivel) {
		this.isVisivel = isVisivel;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImagem() {
		return imagem;
	}
}