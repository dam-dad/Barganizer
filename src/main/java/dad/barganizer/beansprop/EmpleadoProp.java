package dad.barganizer.beansprop;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import dad.barganizer.db.beans.Empleado;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 * 
 * Clase Bean de properties que servirá de apoyo para trabajar con los empleados y representarlos en la interfaz gráfica.
 *
 */
public class EmpleadoProp {
	
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty apellido = new SimpleStringProperty();
	private ObjectProperty<Sexo> genero = new SimpleObjectProperty<Sexo>();
	private ObjectProperty<LocalDate> nacimiento = new SimpleObjectProperty<LocalDate>();
	private ObjectProperty<LocalDate> ingreso = new SimpleObjectProperty<LocalDate>();
	private ObjectProperty<Image> foto = new SimpleObjectProperty<>();
	private StringProperty password = new SimpleStringProperty();
	private ObjectProperty<byte[]> bytesFoto = new SimpleObjectProperty<byte[]>();
	
	public EmpleadoProp() {
		id = new SimpleIntegerProperty(this, "id");
		nombre = new SimpleStringProperty(this, "nombre");
		apellido = new SimpleStringProperty(this, "apellido");
		genero = new SimpleObjectProperty<Sexo>(this, "genero");
		nacimiento = new SimpleObjectProperty<LocalDate>(this, "nacimiento");
		ingreso = new SimpleObjectProperty<LocalDate>(this, "ingreso");
		foto = new SimpleObjectProperty<>(this, "foto");
		password = new SimpleStringProperty(this, "password");
	}

	public EmpleadoProp(Empleado e) {
		id.set(e.getId());
		nombre.set(e.getNombre());
		apellido.set(e.getApellidos());
		genero.set( (e.getGenero().equals("Hombre")) ? Sexo.Hombre:Sexo.Mujer);
		nacimiento.set(e.getFnac());
		ingreso.set(e.getFechaIngreso());
		if (e.getFoto() != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(e.getFoto());
			setBytesFoto(bais.readAllBytes());
			Image f = new Image(new ByteArrayInputStream(e.getFoto()));
			foto.set(f);
		} else {
			foto.set(null);
		}
		
		
		password.set(new String(e.getPass(), StandardCharsets.UTF_8));
	}
	
	@Override
	public String toString() {
		return (getNombre() + " " + getApellido()).trim();
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	

	public final String getNombre() {
		return this.nombreProperty().get();
	}
	

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	

	public final StringProperty apellidoProperty() {
		return this.apellido;
	}
	

	public final String getApellido() {
		return this.apellidoProperty().get();
	}
	

	public final void setApellido(final String apellido) {
		this.apellidoProperty().set(apellido);
	}
	

	public final ObjectProperty<Sexo> generoProperty() {
		return this.genero;
	}
	

	public final Sexo getGenero() {
		return this.generoProperty().get();
	}
	

	public final void setGenero(final Sexo genero) {
		this.generoProperty().set(genero);
	}
	

	public final ObjectProperty<LocalDate> nacimientoProperty() {
		return this.nacimiento;
	}
	

	public final LocalDate getNacimiento() {
		return this.nacimientoProperty().get();
	}
	

	public final void setNacimiento(final LocalDate nacimiento) {
		this.nacimientoProperty().set(nacimiento);
	}
	

	public final ObjectProperty<LocalDate> ingresoProperty() {
		return this.ingreso;
	}
	

	public final LocalDate getIngreso() {
		return this.ingresoProperty().get();
	}
	

	public final void setIngreso(final LocalDate ingreso) {
		this.ingresoProperty().set(ingreso);
	}
	

	public final ObjectProperty<Image> fotoProperty() {
		return this.foto;
	}
	

	public final Image getFoto() {
		return this.fotoProperty().get();
	}
	

	public final void setFoto(final Image foto) {
		this.fotoProperty().set(foto);
	}
	

	public final StringProperty passwordProperty() {
		return this.password;
	}
	

	public final String getPassword() {
		return this.passwordProperty().get();
	}
	

	public final void setPassword(final String password) {
		this.passwordProperty().set(password);
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}
	

	public final int getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

	public final ObjectProperty<byte[]> bytesFotoProperty() {
		return this.bytesFoto;
	}
	

	public final byte[] getBytesFoto() {
		return this.bytesFotoProperty().get();
	}
	

	public final void setBytesFoto(final byte[] bytesFoto) {
		this.bytesFotoProperty().set(bytesFoto);
	}
	
	

	
}

