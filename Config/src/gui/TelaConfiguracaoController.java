package gui;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import alerts.Alertas;
import connection.ConexaoMySQL;
import entitis.Conexao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import util.Animacao;
import util.ProcessaXML;

public class TelaConfiguracaoController implements Initializable {
	
	@FXML
	AnchorPane background;
	
	@FXML
	Button btnExit;
	
	@FXML
	Button btnTestarConexao;
	
	@FXML
	Button btnConfirmar;
	
	@FXML
	Button btnCancelar;
	
	@FXML
	ChoiceBox<String> chBoxBase;
	
	@FXML
	ChoiceBox<String> chBoxDataBase;
	
	@FXML
	TextField txtIP;
	
	@FXML
	TextField txtPorta;
	
	@FXML
	TextField txtUsuario;

	@FXML
	PasswordField pswSenha;
	
	@FXML
	Pane pnImgAviso;
	
	@FXML
	Label lblAviso;
	
	@FXML
	ImageView imgViewConexao;
	
	Conexao dadosConexao;
	
	@FXML
	public void onBtExitAction() {
		System.exit(0);
	}
	
	@FXML
	public void onBtnTesteEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			btnTestarConexao.fire();
		}
	}
	
	@FXML
	public void onBtnTesteClick() {
		background.getScene().getRoot().setCursor(Cursor.WAIT);

		lblAviso.setText("");
		lblAviso.setVisible(false);
		pnImgAviso.setVisible(false);
		chBoxBase.getItems().clear();
		
		if (validaCampos() && Constraints.validaIp(txtIP)) {
			
				Animacao.inicia(imgViewConexao);	
				
				// Cria��o da thread para que esteja validando a conex�o e n�o trave a tela.
				Task<Boolean> verificaConexao = new Task<Boolean>() {
	
					@Override
					protected Boolean call() throws Exception {
						return ConexaoMySQL.testaConexaoMySQL(txtIP.getText().toString(), txtPorta.getText().toString(), 
	 							  chBoxDataBase.getSelectionModel().getSelectedItem().toString(),
	 							  txtUsuario.getText().toString(), pswSenha.getText().toString());
					}
					
					@Override
					protected void succeeded() {
						Animacao.timeline.stop();
						Boolean conectado = getValue();
						
						if (conectado) {
							TelaConfiguracaoController.this.aviso("", "Conectado com sucesso!");

							imgViewConexao.setImage(new Image(getClass().getResourceAsStream("../images/config/icoDataConectado_48.png")));
							imgViewConexao.setFitWidth(48);
							imgViewConexao.setFitHeight(48);
							
							chBoxBase.getItems().addAll(ConexaoMySQL.bases);
							chBoxBase.setDisable(false);
							chBoxBase.getSelectionModel().select(0);
							
						} else {
							TelaConfiguracaoController.this.aviso("", "N�o foi possivel conectar ao banco, verifique os dados de conex�o!");
							
							imgViewConexao.setImage(new Image(getClass().getResourceAsStream("../images/config/icoDataSemConexao_48.png")));
							imgViewConexao.setFitWidth(48);
							imgViewConexao.setFitHeight(48);
						}
						background.getScene().getRoot().setCursor(null);
					}
				};
					Thread t = new Thread(verificaConexao);
					t.setDaemon(true);
					t.start();
		}
	}
	
	@FXML
	public void onBtnCancelarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			btnCancelar.fire();
		}
	}
	
	@FXML
	public void onBtnCancelarClick() {
		if (Alertas.Confirmacao("Sair", "Deseja realmente cancelar? \nToda a altera��o ser� descartada.")) {
			System.exit(0);
		}
	}
	
	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}
	
	@FXML
	public void onBtnConfirmarClick() {
		if (chBoxBase.getSelectionModel().getSelectedIndex() < 0) {
			TelaConfiguracaoController.this.aviso("icoAviso_48.png", "Necess�rio seleceionar uma base, por favor selecione uma base v�lida!");
		} else {
			dadosConexao.setBase(chBoxBase.getSelectionModel().getSelectedItem());
			ProcessaXML.gravaConfig();
		}
	}
	
	@FXML
	public void onTxtPortaEventAction() {
		Constraints.setTxtFieldPort(txtIP);
	}
	
	@FXML
	public void onIpEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			txtPorta.requestFocus();
		}
	}
	
	@FXML
	public void onPortaEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			chBoxDataBase.requestFocus();
		}
	}
	
	@FXML
	public void onDataBaseEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			txtUsuario.requestFocus();
		}
	}
	
	@FXML
	public void onUsuarioEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			pswSenha.requestFocus();
		}
	}
	
	@FXML
	public void onSenhaEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			btnTestarConexao.requestFocus();
		}
	}
	
	public void onTxtIPExitAction() {
		if (txtIP.textProperty().get().toString().isEmpty()) {
			txtIP.setStyle("");
		} else {
			if (Constraints.validaIp(txtIP)) {
				txtIP.setStyle("-fx-border-color: green;");
			} else {
				txtIP.setStyle("-fx-border-color: red;");
			}
		}
	}
	
	public void aviso(String imagem, String texto) {
		lblAviso.setVisible(false);
		pnImgAviso.setVisible(false);
		
		if (!imagem.isEmpty()) {
			ImageView img = new ImageView(new Image(getClass().getResourceAsStream("../images/config/"+imagem)));
			img.setFitWidth(20);
			img.setFitHeight(20);
			
			pnImgAviso.getChildren().clear();
			pnImgAviso.getChildren().add(img);
			pnImgAviso.setVisible(true);
		}
		
		if (!texto.isEmpty()) {
			lblAviso.setText(texto);
			lblAviso.setVisible(true);
		}
	}
	
	public void setConfig(Conexao dadosConexao) {
		this.dadosConexao = dadosConexao;
		
		txtIP.setText(dadosConexao.getHost());
		txtPorta.setText(dadosConexao.getPorta());
		txtUsuario.setText(dadosConexao.getUsuario());
		pswSenha.setText(dadosConexao.getSenha());
		chBoxDataBase.getSelectionModel().select(dadosConexao.getDatabase());
	}
	
	public void processaDados() {
		dadosConexao.setHost(txtIP.getText());
		dadosConexao.setPorta(txtPorta.getText());
		dadosConexao.setBase(chBoxBase.getSelectionModel().getSelectedItem());
		dadosConexao.setUsuario(txtUsuario.getText());
		dadosConexao.setSenha(pswSenha.getText());
		dadosConexao.setDatabase(chBoxDataBase.getSelectionModel().getSelectedItem());
	}
	
	public Boolean validaCampos() {
		Boolean result = false;
		Boolean informe = true; // Utilizado para apenas apresentar uma descri��o.
		
		if (!txtIP.getText().isEmpty() 		&& !txtPorta.getText().isEmpty() &&
		    !txtUsuario.getText().isEmpty() && !pswSenha.getText().isEmpty()) {
			txtIP.setStyle("-fx-border-color: green;");
			txtPorta.setStyle("-fx-border-color: green;");
			txtUsuario.setStyle("-fx-border-color: green;");
			pswSenha.setStyle("-fx-border-color: green;");
			result = true;
		} else {
			if (txtIP.getText().isEmpty()) {
				txtIP.setStyle("-fx-border-color: red;");
				if (informe) {
					
					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe um ip v�lido!");
					informe = false;
				}
			}
			
			if (txtPorta.getText().isEmpty()) {
				txtPorta.setStyle("-fx-border-color: red;");
				
				if (informe) {
					
					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe a porta!");
					informe = false;
				}
			}
			
			if (txtUsuario.getText().isEmpty()) {
				txtUsuario.setStyle("-fx-border-color: red;");
				
				if (informe) {

					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe o usu�rio!");
					informe = false;
				}
			}
			
			if (pswSenha.getText().isEmpty()) {
				pswSenha.setStyle("-fx-border-color: red;");
				
				if (informe) {
					
					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe a senha!");
					informe = false;
				}
			}	
		}	
		return result;
	}
	
	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		Constraints.setTxtFieldPort(txtPorta);
		
		txtIP.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (!newPropertyValue) {
		        	 onTxtIPExitAction();
		        } else {
		        	txtIP.setStyle("");
		        }
		    }
		});
		
		txtPorta.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		    	if (newPropertyValue) { // Usado para limpar o stilo para que pinte quando entra
		    		txtPorta.setStyle("");
		        } else { // Ap�s, na saida faz ent�o a valida��o.
			    	if (txtPorta.textProperty().get().toString().isEmpty()) {
			        	txtPorta.setStyle("");
			        } else {
			        	txtPorta.setStyle("-fx-border-color: green;");
			        }
			    }
		    }
		});
		
		txtUsuario.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){    	
		    	if (newPropertyValue) {
		    		txtUsuario.setStyle("");
		        } else {
			    	if (txtUsuario.textProperty().get().toString().isEmpty()) {
			    		txtUsuario.setStyle("");
			        } else {
			        	txtUsuario.setStyle("-fx-border-color: green;");
			        }
		        }
		    }
		});
		
		pswSenha.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		    	if (newPropertyValue) {
		    		pswSenha.setStyle("");
		        } else {
			    	if (pswSenha.textProperty().get().toString().isEmpty()) {
			        	pswSenha.setStyle("");
			        } else {
			        	pswSenha.setStyle("-fx-border-color: green;");
			        }
		        }
		    }
		});
		
		chBoxDataBase.getItems().add("MySQL");
		chBoxDataBase.getSelectionModel().select("MySQL");
	}
}
