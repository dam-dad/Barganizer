package dad.barganizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.hibernate.HibernateException;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.HibernateUtil;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.TipoPlato;

public class ClaseInsertarDatos {

	public static void main(String[] args) {

//		// INSERTAR BEBIDAS 

		try {
			for (int i = 1; i <= 4; i++) {

				double precio = (double) (Math.random() * (15 - 1 + 1) + 1);
				DecimalFormat df = new DecimalFormat("0.00");
				double p = Double.parseDouble(df.format(precio).replace(',', '.'));
				System.out.println(p);
				File file = new File("C:\\Users\\DANIEL CABRERA\\Desktop\\Imágenes\\Bebidas\\" + i + ".jpg");
				FileInputStream input = new FileInputStream(file);

				TipoPlato tipo = new TipoPlato();
				tipo.setId(3);
				tipo.setNombre("Bebida");

				Carta carta = new Carta();
				carta.setId(1);
				carta.setNombre("Completa");
				FuncionesDB.insertarPlato(HibernateUtil.getSessionFactory().openSession(), "bebida" + i, tipo, p,
						input.readAllBytes(), carta);

			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// INSERTAR ENTRANTES

		try {
			for (int i = 1; i <= 4; i++) {

				double precio = (double) (Math.random() * (15 - 1 + 1) + 1);
				DecimalFormat df = new DecimalFormat("0.00");
				double p = Double.parseDouble(df.format(precio).replace(',', '.'));
				System.out.println(p);
				File file = new File("C:\\Users\\DANIEL CABRERA\\Desktop\\Imágenes\\Entrantes\\" + i + ".jpg");
				FileInputStream input = new FileInputStream(file);

				TipoPlato tipo = new TipoPlato();
				tipo.setId(1);
				tipo.setNombre("Entrante");

				Carta carta = new Carta();
				carta.setId(1);
				carta.setNombre("Completa");

				FuncionesDB.insertarPlato(HibernateUtil.getSessionFactory().openSession(), "entrante" + i, tipo, p,
						input.readAllBytes(), carta);

			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// INSERTAR PRINCIPALES COMPLETOS

		try {
			for (int i = 1; i <= 4; i++) {

				double precio = (double) (Math.random() * (15 - 1 + 1) + 1);
				DecimalFormat df = new DecimalFormat("0.00");
				double p = Double.parseDouble(df.format(precio).replace(',', '.'));
				System.out.println(p);
				File file = new File("C:\\Users\\DANIEL CABRERA\\Desktop\\Imágenes\\Principales\\" + i + ".jpg");
				FileInputStream input = new FileInputStream(file);

				TipoPlato tipo = new TipoPlato();
				tipo.setId(2);
				tipo.setNombre("Principal");

				Carta carta = new Carta();
				carta.setId(1);
				carta.setNombre("Completa");

				FuncionesDB.insertarPlato(HibernateUtil.getSessionFactory().openSession(), "principal" + i, tipo, p,
						input.readAllBytes(), carta);

			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//INSERTAR PRINCIPALES VEGETARIANOS

		try {
			for (int i = 5; i <= 6; i++) {

				double precio = (double) (Math.random() * (15 - 1 + 1) + 1);
				DecimalFormat df = new DecimalFormat("0.00");
				double p = Double.parseDouble(df.format(precio).replace(',', '.'));
				System.out.println(p);
				File file = new File("C:\\Users\\DANIEL CABRERA\\Desktop\\Imágenes\\Principales\\" + i + ".jpg");
				FileInputStream input = new FileInputStream(file);

				TipoPlato tipo = new TipoPlato();
				tipo.setId(2);
				tipo.setNombre("Principal");

				Carta carta = new Carta();
				carta.setId(2);
				carta.setNombre("Vegetariana");

				FuncionesDB.insertarPlato(HibernateUtil.getSessionFactory().openSession(), "principal" + i, tipo, p,
						input.readAllBytes(), carta);

			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// INSERTAR POSTRES

		try {
			for (int i = 1; i <= 4; i++) {

				double precio = (double) (Math.random() * (15 - 1 + 1) + 1);
				DecimalFormat df = new DecimalFormat("0.00");
				double p = Double.parseDouble(df.format(precio).replace(',', '.'));
				System.out.println(p);
				File file = new File("C:\\Users\\DANIEL CABRERA\\Desktop\\Imágenes\\Postres\\" + i + ".jpg");
				FileInputStream input = new FileInputStream(file);

				TipoPlato tipo = new TipoPlato();
				tipo.setId(4);
				tipo.setNombre("Postre");

				Carta carta = new Carta();
				carta.setId(1);
				carta.setNombre("Completa");

				FuncionesDB.insertarPlato(HibernateUtil.getSessionFactory().openSession(), "postre" + i, tipo, p,
						input.readAllBytes(), carta);

			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
