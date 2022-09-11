package model.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Mail {
	SimpleEmail email = new SimpleEmail();

	public static void main(String[] args) throws EmailException {
		sendEmail();
	}

	public static void sendEmail() throws EmailException {
		
		String user = "";
		String password = "";
		
		SimpleEmail email = new SimpleEmail();
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setStartTLSEnabled(true);
		email.addTo("dev.hideyukitakahashi@gmail.com"); //pode ser qualquer um email
		email.setAuthentication(user, password); // esse campo precisa ser preenchido com dados reais
		email.setFrom("teste@net"); //aqui necessita ser o email que voce fara a autenticacao
		email.setSubject("Teste");
		email.setMsg("Mensagem Testando");
		email.send();

	}

}
