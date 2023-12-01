package Jogo.Componentes.Fases;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;

import Jogo.Container;
import Jogo.Componentes.Inimigos.Alien;
import Jogo.Componentes.Inimigos.Drakthar;
import Jogo.Componentes.Inimigos.Robo;

import Jogo.Componentes.Jogadores.Jogador1;
import Jogo.Componentes.Jogadores.Jogador2;
import Jogo.Componentes.Jogadores.TiroNave;
import Jogo.Componentes.Objetos.PowerUp;

public class Fase extends JPanel {

	private int contador = 0;
	
	public static boolean doisJogadores;

	private boolean vitoria;
	private boolean gameOver;

	public Fase(Container container) {
		this.vitoria = false;
		this.gameOver = false;
	}

	public void drawComponentesIniciais(Graphics2D graficos, Jogador1 jogador1, Jogador2 jogador2){

		if (jogador1.isVisivel()) {
			graficos.drawImage(jogador1.getImagem(), jogador1.getX(), jogador1.getY(), this);
			jogador1.drawTiroNave(graficos);
		}

		if (doisJogadores == true) {
			if (jogador2.isVisivel()) {
				graficos.drawImage(jogador2.getImagem(), jogador2.getX(), jogador2.getY(), this);
				jogador2.drawTiroNave(graficos);
			}
		} else {
			jogador2.setVisivel(false);
		}
	}

	public Font loadFont(String path, float size) {
		try {
			File fontFile = new File(path);
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

			return font.deriveFont(size);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			return new Font("Arial", Font.PLAIN, (int) size);
		}
	}

	public void checarRobo(Robo robo) {
		if (robo.getVida() == 0) {
			robo.setVisivel(false);
		}
	}

	public void checarAlien(Alien alien) {
		if (alien.getVida() == 0) {
			alien.setVisivel(false);
		}
	}

	public void checarDrakthar(Drakthar drakthar) {
		if (drakthar.getVida() == 0) {
			drakthar.setVisivel(false);
		}
	}

	public void checarRobos(List<Robo> robos) {
		for (int i = 0; i < robos.size(); i++) {
			Robo tempRobo = robos.get(i);
			if (tempRobo.getVida() == 0) {
				tempRobo.setVisivel(false);
			}
		}
	}

	public boolean checarJogadores(Jogador1 jogador1, Jogador2 jogador2, boolean doisJogadores) {
		if (jogador1.getVida() <= 0 && jogador2.getVida() <= 0) {
			gameOver = true;
		}
		if (jogador1.getVida() <= 0 && doisJogadores == false) {
			gameOver = true;
		}
		return gameOver;
	}

	// Colisões de naves e power ups:
	public void colisoesPowerUps(List<PowerUp> powerUps, Jogador1 jogador1, Jogador2 jogador2) {
		for (int i = 0; i < powerUps.size(); i++) {
			PowerUp tempPowerUp = powerUps.get(i);
			tempPowerUp.colisaoPowerUp(jogador1);
			tempPowerUp.colisaoPowerUp(jogador2);
		}
	}

	// Colisões de naves e robos:
	public void colisoesNavesRobos(List<Robo> robos, Jogador1 jogador1, Jogador2 jogador2) {
		for (int i = 0; i < robos.size(); i++) {
			Robo tempRobo = robos.get(i);
			tempRobo.colisaoNaveRobo(jogador1);
			tempRobo.colisaoNaveRobo(jogador2);
		}
	}

	// Colisões de tiros(naves) e robôs:
	public void colisoesTiroEmRobo1(Robo robo, Jogador1 jogador1, Jogador2 jogador2, int x) {
		List<TiroNave> tiros1 = jogador1.getTiros();
		List<TiroNave> tiros2 = jogador2.getTiros();

		for (int j = 0; j < tiros1.size(); j++) {
			if (robo.getX() == x) {
				robo.colisaoRoboTiro(jogador1, j);
			}
		}
		for (int j = 0; j < tiros2.size(); j++) {
			if (robo.getX() == x) {
				robo.colisaoRoboTiro(jogador2, j);
			}
		}
	}

	// Colisões de tiros(naves) e robôs de colisão:
	public void colisoesTiroEmRobo2(Jogador1 jogador, List<Robo> robos) {
		List<TiroNave> tiros = jogador.getTiros();

		for (int j = 0; j < tiros.size(); j++) {
			for (int i = 0; i < robos.size(); i++) {
				Robo tempRobo = robos.get(i);
				tempRobo.colisaoRoboTiro(jogador, j);
			}
		}
	}

	public void colisoesTiroEmRobo2(Jogador2 jogador, List<Robo> robos) {
		List<TiroNave> tiros = jogador.getTiros();

		for (int j = 0; j < tiros.size(); j++) {
			for (int i = 0; i < robos.size(); i++) {
				Robo tempRobo = robos.get(i);
				tempRobo.colisaoRoboTiro(jogador, j);
			}
		}
	}

	// Colisões de tiros(naves) e aliens:
	public void colisoesTiroEmAlien(Alien alien, Jogador1 jogador1, Jogador2 jogador2, int x) {
		if (alien.getX() == x) {
			List<TiroNave> tiros1 = jogador1.getTiros();
			for (int j = 0; j < tiros1.size(); j++) {
				alien.colisaoAlienTiro(jogador1, j);
			}

			List<TiroNave> tiros2 = jogador2.getTiros();
			for (int j = 0; j < tiros2.size(); j++) {
				alien.colisaoAlienTiro(jogador2, j);
			}
		}
	}

	public void colisoesTiroEmDrakthar(Drakthar drakthar, Jogador1 jogador, int x) {
		if (drakthar.getX() == x) {
			List<TiroNave> tiros = jogador.getTiros();
			for (int j = 0; j < tiros.size(); j++) {
				drakthar.colisaoDraktharTiro(jogador, j);

			}
		}
	}

	public void colisoesTiroDrakthar(Drakthar drakthar, Jogador2 jogador, int x) {
		if (drakthar.getX() == x) {
			List<TiroNave> tiros = jogador.getTiros();
			for (int j = 0; j < tiros.size(); j++) {
				drakthar.colisaoDraktharTiro(jogador, j);
			}
		}
	}
}