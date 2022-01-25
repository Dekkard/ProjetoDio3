package dio.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Builder
@Table(value = "telefone")
public class Telefone {
	@Getter
	@AllArgsConstructor
	public enum TipoTelefone{
		CASA("Casa"),
		COMERCIAL("Comercial"),
		CELULAR("Celular");
		private final String tipo;
	}
	@Id
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	@Column(name = "ddi")
	private Integer ddi;
	@Column(name = "ddd")
	private Integer ddd;
	@Column(name = "numero")
	private Long numero;
	@Column(name = "tipotelefone")
	private TipoTelefone tipotelefone;
	@Column(name = "fkcpf")
	private Long fkcpf;
	
	public Telefone() {
		
	}
	
	public Telefone(Long id, Integer ddi, Integer ddd, Long numero, TipoTelefone tipotelefone, Long fkcpf) {
		this.id = id;
		this.ddi = ddi;
		this.ddd = ddd;
		this.numero = numero;
		this.tipotelefone = tipotelefone;
		this.fkcpf = fkcpf;
	}
	
	public String getNumeroFormat() {
		String num = String.valueOf(this.numero);
		if (num.length() == 8) {
			Matcher m = Pattern.compile("(\\d{4})(\\d{4})").matcher(num);
			if (m.find()) {
				return m.group(1) + "-" + m.group(2);
			}
		} else if (num.length() == 9) {
			Matcher m = Pattern.compile("(\\d{1})(\\d{4})(\\d{4})").matcher(num);
			if (m.find()) {
				return m.group(1) + " " + m.group(2) + "-" + m.group(3);
			}
		} else {
			return null;
		}
		return null;
	}

	public String getTelefone(boolean inc_ddd, boolean inc_ddi) {
		return (inc_ddi ? "+" + this.ddi + " " : "") + (inc_ddd ? "(" + this.ddd + ") " : "") + getNumero();
	}
}
