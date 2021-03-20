package main.java.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    @FXML private TextField accountNameTextField;
    @FXML private TextField accountSoldTextField;

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

    @FXML private Button accountUpdateButton;
    @FXML private Button transactionUpdateButton;




    public void initializare(Stage stage, ApiInterface apiInterface, User user) {

        this.apiInterface=apiInterface;
        this.stage=stage;
        this.service=new Service(user,apiInterface);
        ShowAccountTable();

        accountUpdateButton.setDisable(true);
        transactionUpdateButton.setDisable(true);



        accountTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    try {
                        ShowTransactionTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(mouseEvent.getClickCount() == 2){
                        try {
                            CompleteAccount();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


        transactionTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                    transactionUpdateButton.setDisable(true);
                    transactionSoldTextField.setEditable(true);

                    if(mouseEvent.getClickCount() == 2){
                        CompleteTransactionFields();
                    }
                }
            }
        });
    }

    public void CompleteAccount() throws IOException {

        accountUpdateButton.setDisable(false);
        accountSoldTextField.setEditable(false);


        accountNameTextField.setText(accountTable.getSelectionModel().getSelectedItem().getName());
        accountSoldTextField.setText(String.valueOf(accountTable.getSelectionModel().getSelectedItem().getSold()));



    }

    public void CompleteTransactionFields(){
        transactionUpdateButton.setDisable(false);
        transactionSoldTextField.setEditable(false);

        transactionTitleTextField.setText(transactionTable.getSelectionModel().getSelectedItem().getTitle());
        transactionDetailsTextField.setText(transactionTable.getSelectionModel().getSelectedItem().getDetails());
        transactionSoldTextField.setText(String.valueOf(transactionTable.getSelectionModel().getSelectedItem().getSold()));

    }


    public void ShowTransactionTable() throws IOException {


        transactionTitleTextField.clear();
        transactionDetailsTextField.clear();
        transactionSoldTextField.clear();


        accountSoldTextField.setEditable(true);
        accountUpdateButton.setDisable(true);




        transactionTable.getItems().clear();
        Iterable<Transaction> iterableTransaction= null;
        iterableTransaction = service.findAllTransaction(accountTable.getSelectionModel().getSelectedItem().getId());
        ObservableList<Transaction> transactionList= FXCollections.observableArrayList();


        for(Transaction x:iterableTransaction){
            transactionList.add(x);
        }

        this.transactionIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
        this.transactionTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        this.transactionSoldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getSold())));
        this.transactionDetailsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetails()));
        this.transactionDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUpdated_at()));

        transactionTable.setItems(transactionList);



    }

    public void AddAccount() throws IOException {


        Account account=new Account(accountNameTextField.getText(),Integer.parseInt(accountSoldTextField.getText()));

        int code=service.AddAccount(account);
        if(code==201){
            ShowAccountTable();
            accountNameTextField.clear();
            accountSoldTextField.clear();
        }
        else{
            ShowError("Something went wrong",code);
        }
    }

    public void DeleteAccount() throws IOException {

       int id= accountTable.getSelectionModel().getSelectedItem().getId();
       int code=service.DeleteAccount(id);
        if(code==200){
            ShowAccountTable();
            accountNameTextField.clear();
            accountSoldTextField.clear();
        }
        else{
            ShowError("Something went wrong",code);
        }

    }

    public void UpdateAccount() throws IOException {

        Account account=new Account(accountNameTextField.getText(),Integer.parseInt(accountSoldTextField.getText()));

        account.setId(accountTable.getSelectionModel().getSelectedItem().getId());
        int code=service.UpdateAccount(account);
        if(code==200){
            ShowAccountTable();
            transactionTable.getItems().clear();
            accountNameTextField.clear();
            accountSoldTextField.clear();
        }
        else{
            ShowError("Something went wrong",code);
        }


    }




    public void AddTransaction() throws IOException {

        Transaction transaction =new Transaction(transactionTitleTextField.getText(),transactionDetailsTextField.getText(),Integer.parseInt(transactionSoldTextField.getText()));
        transaction.setAccountId(accountTable.getSelectionModel().getSelectedItem().getId());
        int code=service.AddTransaction(transaction);
        if(code==201){
            ShowTransactionTable();
            transactionTitleTextField.clear();
            transactionDetailsTextField.clear();
            transactionSoldTextField.clear();
        }

        else{
            ShowError("Something went wrong",code);
        }

    }

    public void UpdateTransaction() throws IOException {



        Transaction transaction =new Transaction(transactionTitleTextField.getText(),transactionDetailsTextField.getText(),Integer.parseInt(transactionSoldTextField.getText()));

        transaction.setId(transactionTable.getSelectionModel().getSelectedItem().getId());
        transaction.setAccountId(accountTable.getSelectionModel().getSelectedItem().getId());

        int code=service.UpdateTransaction(transaction);
        if(code==200){
            ShowTransactionTable();
            transactionTitleTextField.clear();
            transactionDetailsTextField.clear();
            transactionSoldTextField.clear();
        }
        else{
            ShowError("Something went wrong",code);
        }


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

        accountTable.setItems(accountList);


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
