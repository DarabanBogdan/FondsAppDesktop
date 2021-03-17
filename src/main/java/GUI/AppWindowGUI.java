package main.java.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.Domain.Account;
import main.java.Domain.Transaction;
import main.java.Domain.User;
import main.java.Retrofit.ApiInterface;
import main.java.Service.Service;

import java.io.IOException;

public class AppWindowGUI {

    private Service service;
    private ApiInterface apiInterface;
    private  Stage stage;

    @FXML private TableColumn<Account,String> accountIdColumn;
    @FXML private TableColumn<Account,String> accountNameColumn;
    @FXML private TableColumn<Account,String> accountSoldColumn;
    @FXML private TableColumn<Account,String> accountDebtColumn;
    @FXML private TextField accountNameTextField;
    @FXML private TextField accountSoldTextField;
    @FXML private CheckBox accountDebtCheckBox;

    @FXML private TableColumn<Transaction,String> transactionIdColumn;
    @FXML private TableColumn<Transaction,String> transactionTitleColumn;
    @FXML private TableColumn<Transaction,String> transactionDetailsColumn;
    @FXML private TableColumn<Transaction,String> transactionSoldColumn;
    @FXML private TableColumn<Transaction,String> transactionDateColumn;

    @FXML private TextField transactionTitleTextField;
    @FXML private TextField transactionDetailsTextField;
    @FXML private TextField transactionSoldTextField;


    @FXML private TableView<Account> accountTable;
    @FXML private TableView<Transaction> transactionTable;





    public void initializare(Stage stage, ApiInterface apiInterface, User user) {

        this.apiInterface=apiInterface;
        this.stage=stage;
        this.service=new Service(user,apiInterface);
        ShowAccountTable();
    }

    public void AddAccount(){

        Account account=new Account(accountNameTextField.getText(),Integer.getInteger(accountSoldTextField.getText()));
        if(accountDebtCheckBox.isSelected())
            account.setDebt(1);
        else
            account.setDebt(0);

        int code=service.AddAccount(account);
        if(code==201){
            ShowAccountTable();
            accountNameTextField.clear();
            accountSoldTextField.clear();
            accountDebtCheckBox.setSelected(false);
        }
        else{
            ShowError("Something went wrong",code);
        }
    }

    public void DeleteAccount(){


    }

    public void UpdateAccount(){


    }


    public void IncreaseTransaction(){



    }

    public void DecreaseTransaction(){


    }

    public void UpdateTransaction(){


    }


    public void ShowAccountTable(){

        accountTable.getItems().clear();
        Iterable<Account> iterableAccount= null;
        try {
            iterableAccount = service.findAllAccounts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<Account> accountList= FXCollections.observableArrayList();


        for(Account x:iterableAccount){
            accountList.add(x);
        }

        this.accountIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
        this.accountNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        this.accountSoldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getSold())));
        this.accountDebtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().isDebt())));

        accountTable.setItems(accountList);


    }

    public void ShowTransactionTable(){


    }
    public void Logout() throws IOException {

        Parent root;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(LoginGUI.class.getResource("/Login.fxml"));
        LoginGUI afis=new LoginGUI(stage,apiInterface);
        loader.setController(afis);
        root = loader.load();
        stage.setTitle("Login");
        stage.setScene(new Scene(root ));


    }

    public void ShowError(String message,int code){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText("Server returned the next error code:"+ code);

        alert.showAndWait();

    }
}
