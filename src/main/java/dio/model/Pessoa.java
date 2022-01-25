package dio.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(value = "pessoa")
public class Pessoa {
	@Id
	@Column(name = "cpf", unique = true, nullable = false)
	private Long cpf;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "nascimento")
	private Date nascimento;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "email")
	private String email;
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private List<Telefone> telefone = new ArrayList<>();

	public Pessoa() {
	}

	public Pessoa(Long cpf, String nome, Date nascimento, String endereco, String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.nascimento = nascimento;
		this.endereco = endereco;
		this.email = email;
	}

	@Autowired
	public Pessoa(Long cpf, String nome, Date nascimento, String endereco, String email, List<Telefone> telefone) {
		this.nome = nome;
		this.cpf = cpf;
		this.nascimento = nascimento;
		this.endereco = endereco;
		this.email = email;
		this.telefone = telefone;
	}

	public String getCpfFormat() {
		String id = String.valueOf(this.cpf);
		if (id.length() < 11)
			id = "0".repeat(11 - id.length()) + id;
		Matcher m = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})").matcher(id);
		if (m.find()) {
			return m.group(1) + "." + m.group(2) + "." + m.group(3) + "-" + m.group(4);
		}
		return null;
	}

	/*
	 * public void setNascimento(String dataNasc) { try { this.nascimento = new
	 * SimpleDateFormat("dd/MM/yyyy").parse(dataNasc); } catch (ParseException e) {
	 * System.out.println("Data inválida"); } }
	 */

	public String getNascimentoFormat() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this.nascimento);
	}

	public String toString() {
		return "Nome: " + this.nome + "\nCPF: " + getCpf() + "\nData de Nascimento: " + getNascimentoFormat()
				+ "\nEndereço: " + this.endereco + "\nE-mail: " + this.email;
	}
}
