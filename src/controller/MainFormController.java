package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Balance;
import model.Contribution;
import model.DBConnector;
import model.DataAccessor;
import model.Organisation;
import model.Payment;
import model.TooltippedTableCell;
import model.User;

/**
 * FXML Controller class
 *
 * @author Bazhenov_PA
 */
public class MainFormController implements Initializable {

    @FXML
    private AnchorPane controlButtons;
    @FXML
    private Button balanceButton;
    @FXML
    private Button paymentsButton;
    @FXML
    private Button refBookButton;
    @FXML
    private Button settingsButton;
    @FXML
    private AnchorPane content;
    @FXML
    private AnchorPane balancePane;
    @FXML
    private Label balanceField;
    @FXML
    private TableView<Balance> balanceTable;
    @FXML
    private TableColumn<Balance, Integer> balanceMoneyColumn;
    @FXML
    private TableColumn<Balance, String> balanceDateColumn;
    @FXML
    private AnchorPane paymentsPane;
    @FXML
    private TableView<Payment> paymentsTable;
    @FXML
    private TableColumn<Payment, Integer> paymentsMoneyColumn;
    @FXML
    private TableColumn<Payment, String> paymentsOrgColumn;
    @FXML
    private TableColumn<Payment, String> paymentsDateColumn;
    @FXML
    private TextField paymentsMoneyField;
    @FXML
    private Button paymentsAddButton;
    @FXML
    private AnchorPane refBookPane;
    @FXML
    private TableView<User> refBookUsersTable;
    @FXML
    private TableColumn<User, String> refBookUserColumn;
    @FXML
    private TableView<Organisation> refBookOrgTable;
    @FXML
    private TableColumn<Organisation, String> refBookOrgTableNameColumn;
    @FXML
    private TableColumn<Organisation, String> refBookOrgTablePhoneColumn;
    @FXML
    private TableColumn<Organisation, Integer> refBookOrgTablePriceColumn;
    @FXML
    private TableColumn<Organisation, String> refBookOrgTableCommentColumn;
    @FXML
    private AnchorPane settingsPane;
    @FXML
    private TableView<User> settingsUserTable;
    @FXML
    private TableColumn<User, String> settingsUserColumn;
    @FXML
    private TextField settingsUserAddField;
    @FXML
    private Button settingsUserAddButton;
    @FXML
    private Button settingsUserDeleteButton;
    @FXML
    private TableView<Organisation> settingsOrgTable;
    @FXML
    private TableColumn<Organisation, String> settingsOrgNameColumn;
    @FXML
    private TableColumn<Organisation, Integer> settingsOrgPriceColumn;
    @FXML
    private TableColumn<Organisation, String> settingsOrgPhoneColumn;
    @FXML
    private TableColumn<Organisation, String> settingsOrgCommentColumn;
    @FXML
    private TextField settingsOrgAddNameField;
    @FXML
    private TextField settingsOrgAddPriceField;
    @FXML
    private TextField settingsOrgAddPhoneField;
    @FXML
    private TextField settingsOrgAddCommentField;
    @FXML
    private Button settingsOrgAddButton;
    @FXML
    private Button settingsOrgDeleteButton;
    @FXML
    private ComboBox<Organisation> settingsDefaultOrgButton;
    @FXML
    private AnchorPane messagePane;
    @FXML
    private Button messageOkButton;
    @FXML
    private TextArea messageTextField;
    @FXML
    private AnchorPane helloPane;
    @FXML
    private Button contributionsButton;
    @FXML
    private AnchorPane contributionsPane;
    @FXML
    private TextField contributionsMoneyField;
    @FXML
    private ComboBox<User> contributionsUserField;
    @FXML
    private Button contributionsMakeButton;
    @FXML
    private TableView<Contribution> contributionsNeedToPayTable;
    @FXML
    private TableColumn<Contribution, String> contributionsUserNTPColumn;
    @FXML
    private TableColumn<Contribution, String> contributionsDateNTPColumn;
    @FXML
    private TableView<Contribution> contributionsTable;
    @FXML
    private TableColumn<Contribution, String> contributionsUserColumn;
    @FXML
    private TableColumn<Contribution, String> contributionsDateColumn;
    @FXML
    private TableColumn<Contribution, Integer> contributionsMoneyColumn;

    private ObservableList<Balance> balanceList;
    private DataAccessor dbAccessor;
    private User selectedUser;
    private Organisation selectedOrganisation;
    // WarningAnswer = 2 - wait for answer
    // WarningAnswer = 1 - yes answer
    // Warning Answer = 0 - no answer

    /**
     * Initializes the controller class.
     */
    public void showPane(AnchorPane pane, Button button) {
        balancePane.setVisible(false);
        paymentsPane.setVisible(false);
        contributionsPane.setVisible(false);
        settingsPane.setVisible(false);
        refBookPane.setVisible(false);
        helloPane.setVisible(false);
        pane.setVisible(true);
        balanceButton.setDisable(false);
        paymentsButton.setDisable(false);
        contributionsButton.setDisable(false);
        settingsButton.setDisable(false);
        refBookButton.setDisable(false);
        button.setDisable(true);
    }

    public void showPane(AnchorPane pane) {
        balancePane.setVisible(false);
        paymentsPane.setVisible(false);
        contributionsPane.setVisible(false);
        settingsPane.setVisible(false);
        refBookPane.setVisible(false);
        helloPane.setVisible(false);
        pane.setVisible(true);
        balanceButton.setDisable(false);
        paymentsButton.setDisable(false);
        contributionsButton.setDisable(false);
        settingsButton.setDisable(false);
        refBookButton.setDisable(false);
    }

    public void showMessage(String message) {
        messagePane.setVisible(true);
        messageTextField.setText(message);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showPane(helloPane);
        try {
            DBConnector.setConnection();
        } catch (SQLException ex) {
            showMessage(ex.toString());
        }
        dbAccessor = new DataAccessor();
        selectedUser = null;
        selectedOrganisation = null;
        balanceDateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        balanceMoneyColumn.setCellValueFactory(new PropertyValueFactory("moneyAmount"));
        paymentsDateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        paymentsMoneyColumn.setCellValueFactory(new PropertyValueFactory("moneyAmount"));
        paymentsOrgColumn.setCellValueFactory(new PropertyValueFactory("organisationName"));
        contributionsUserColumn.setCellValueFactory(new PropertyValueFactory("userName"));
        contributionsDateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        contributionsMoneyColumn.setCellValueFactory(new PropertyValueFactory("moneyAmount"));
        contributionsDateNTPColumn.setCellValueFactory(new PropertyValueFactory("date"));
        contributionsUserNTPColumn.setCellValueFactory(new PropertyValueFactory("userName"));
        refBookUserColumn.setCellValueFactory(new PropertyValueFactory("name"));
        refBookOrgTablePriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        refBookOrgTableCommentColumn.setCellValueFactory(new PropertyValueFactory("comment"));
        refBookOrgTableNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        refBookOrgTablePhoneColumn.setCellValueFactory(new PropertyValueFactory("phone"));
        refBookOrgTableCommentColumn.setCellFactory(TooltippedTableCell.forTableColumn());
        refBookOrgTableNameColumn.setCellFactory(TooltippedTableCell.forTableColumn());
        refBookOrgTablePhoneColumn.setCellFactory(TooltippedTableCell.forTableColumn());
        settingsUserColumn.setCellValueFactory(new PropertyValueFactory("name"));
        settingsOrgPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        settingsOrgCommentColumn.setCellValueFactory(new PropertyValueFactory("comment"));
        settingsOrgNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        settingsOrgPhoneColumn.setCellValueFactory(new PropertyValueFactory("phone"));
        settingsOrgCommentColumn.setCellFactory(TooltippedTableCell.forTableColumn());
        settingsOrgNameColumn.setCellFactory(TooltippedTableCell.forTableColumn());
        settingsOrgPhoneColumn.setCellFactory(TooltippedTableCell.forTableColumn());
    }

    @FXML
    private void showBalancePane(ActionEvent event) {
        showPane(balancePane, balanceButton);
        try {
            balanceTable.setItems(dbAccessor.getAllBalance());
            balanceField.setText(String.valueOf(dbAccessor.getCurrentBalance().getMoneyAmount()));

        } catch (SQLException ex) {
            showMessage(ex.toString());
        }
    }

    @FXML
    private void showPaymentsPane(ActionEvent event) {
        showPane(paymentsPane, paymentsButton);
        try {
            paymentsTable.setItems(dbAccessor.getPayments());
        } catch (SQLException ex) {
            showMessage(ex.toString());
        }
    }

    @FXML
    private void showContributionsPane(ActionEvent event) {
        showPane(contributionsPane, contributionsButton);
        try {
            contributionsUserField.setValue(null);
            contributionsMoneyField.setText("");
            contributionsTable.setItems(dbAccessor.getContributions());
            contributionsNeedToPayTable.setItems(dbAccessor.getLastContributionsByUser());
            contributionsUserField.setItems(dbAccessor.getAllUsers());
        } catch (SQLException ex) {
            showMessage(ex.toString());
        }
    }

    @FXML
    private void showRefBookPane(ActionEvent event) {
        showPane(refBookPane, refBookButton);
        try {
            refBookUsersTable.setItems(dbAccessor.getAllUsers());
            refBookOrgTable.setItems(dbAccessor.getAllOrganisations());
        } catch (SQLException ex) {
            showMessage(ex.toString());
        }
    }

    @FXML
    private void showSettingsPane(ActionEvent event) {
        showPane(settingsPane, settingsButton);
        try {
            settingsOrgTable.setItems(dbAccessor.getAllOrganisations());
            settingsUserTable.setItems(dbAccessor.getAllUsers());
            settingsDefaultOrgButton.setItems(dbAccessor.getAllOrganisations());
            settingsDefaultOrgButton.setValue(dbAccessor.getDefaultOrganisation());
        } catch (SQLException ex) {
            showMessage(ex.toString());
        }
    }

    @FXML
    private void contributeCash(ActionEvent event) {
        String moneyFromField = contributionsMoneyField.getText();
        if (!moneyFromField.equals("") && moneyFromField.matches("[0-9]+")) {
            if (contributionsUserField.getValue() != null) {
                try {
                    int moneyAmount = Integer.parseInt(moneyFromField);
                    contributionsMakeButton.setDisable(true);
                    dbAccessor.makeContribution(contributionsUserField.getValue(), moneyAmount);
                    showMessage("Взнос успешно добавлен!");
                    contributionsTable.setItems(dbAccessor.getContributions());
                    contributionsNeedToPayTable.setItems(dbAccessor.getLastContributionsByUser());
                    contributionsUserField.setValue(null);
                    contributionsMoneyField.setText("");
                    contributionsMakeButton.setDisable(false);
                } catch (SQLException ex) {
                    showMessage(ex.toString());
                }

            } else {
                showMessage("Ошибка: Поле участник заполнено некорректно!");
            }
        } else {
            showMessage("Ошибка: Поле Сумма заполнено некорректно!");
        }
    }

    @FXML
    private void makePayment(ActionEvent event) {
        String moneyFromField = paymentsMoneyField.getText();
        if (!moneyFromField.equals("") && moneyFromField.matches("[0-9]+")) {
            try {
                int moneyAmount = Integer.parseInt(moneyFromField);
                paymentsAddButton.setDisable(true);
                dbAccessor.makePayment(moneyAmount);
                showMessage("Платеж успешно добавлен!");
                paymentsTable.setItems(dbAccessor.getPayments());
                paymentsAddButton.setDisable(false);
                paymentsMoneyField.setText("");
            } catch (SQLException ex) {
                showMessage(ex.toString());
            }
        } else {
            showMessage("Ошибка: Поле Сумма заполнено некорректно!");
        }
    }

    @FXML
    private void selectUserSettingsPane(MouseEvent event) {
        selectedUser = settingsUserTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            settingsUserAddField.setText(selectedUser.getName());
            settingsUserAddButton.setText("Изменить");
            settingsUserDeleteButton.setDisable(false);
        }
    }

    @FXML
    private void addEditUser(ActionEvent event) {
        if (!settingsUserAddField.getText().equals("")) {
            try {
                if (selectedUser != null) {
                    selectedUser.setName(settingsUserAddField.getText());
                    dbAccessor.updateUser(selectedUser);
                    showMessage("Пользователь успешно отредактирован!");
                } else {
                    User newUser = new User();
                    newUser.setName(settingsUserAddField.getText());
                    dbAccessor.addUser(newUser);
                    showMessage("Пользователь успешно добавлен!");
                }
                settingsUserTable.setItems(dbAccessor.getAllUsers());
                settingsUserAddField.setText("");
            } catch (SQLException ex) {
                showMessage(ex.toString());
            }
        } else {
            showMessage("Ошибка: Поле имя не заполнено!");
        }
        
    }

    @FXML
    private void removeUser(ActionEvent event) {
        if (selectedUser != null) {
            try {
                dbAccessor.removeUser(selectedUser);
                showMessage("Пользователь успешно удален!");
                settingsUserTable.setItems(dbAccessor.getAllUsers());
            } catch (SQLException ex) {
                showMessage(ex.toString());
            }
        } else {
            showMessage("Пользователь не выбран!");
        }
    }

    @FXML
    private void selectOrganisation(MouseEvent event) {
        selectedOrganisation = settingsOrgTable.getSelectionModel().getSelectedItem();
        if (selectedOrganisation != null) {
            settingsOrgAddCommentField.setText(selectedOrganisation.getComment());
            settingsOrgAddNameField.setText(selectedOrganisation.getName());
            settingsOrgAddPhoneField.setText(selectedOrganisation.getPhone());
            settingsOrgAddPriceField.setText(String.valueOf(selectedOrganisation.getPrice()));
            settingsOrgDeleteButton.setDisable(false);
            settingsOrgAddButton.setText("Изменить");
        }
    }

    @FXML
    private void addEditOrganisation(ActionEvent event) {
        if (!settingsOrgAddPriceField.getText().equals("")
                    && !settingsOrgAddNameField.getText().equals("")
                    && !settingsOrgAddPhoneField.getText().equals("")) {
            if (settingsOrgAddPriceField.getText().matches("[0-9]+")) {
                try {
                    if (selectedOrganisation != null) {
                        selectedOrganisation.setComment(settingsOrgAddCommentField.getText());
                        selectedOrganisation.setName(settingsOrgAddNameField.getText());
                        selectedOrganisation.setPhone(settingsOrgAddPhoneField.getText());
                        selectedOrganisation.setPrice(Integer.parseInt(settingsOrgAddPriceField.getText()));
                        dbAccessor.updateOrganisation(selectedOrganisation);
                        showMessage("Организация успешно отредактирована!");
                    } else {
                        Organisation newOrg = new Organisation();
                        newOrg.setComment(settingsOrgAddCommentField.getText());
                        newOrg.setIsdefault(false);
                        newOrg.setName(settingsOrgAddNameField.getText());
                        newOrg.setPhone(settingsOrgAddPhoneField.getText());
                        newOrg.setPrice(Integer.parseInt(settingsOrgAddPriceField.getText()));
                        dbAccessor.addOrganisation(newOrg);
                        showMessage("Организация успешно добавлена!");
                    }
                    settingsOrgTable.setItems(dbAccessor.getAllOrganisations());
                    settingsOrgAddCommentField.setText("");
                    settingsOrgAddNameField.setText("");
                    settingsOrgAddPriceField.setText("");
                    settingsOrgAddPhoneField.setText("");
                } catch (SQLException ex) {
                    showMessage(ex.toString());
                }
            } else {
                showMessage("Ошибка: Поле стоимость должно содержать только цифры.");
            }
        } else {
            showMessage("Ошибка: Одно или несколько обязательных полей 'Стоимость','Наименование','Телефон' не заполнены.");
        }
    }

    @FXML
    private void removeOrganisation(ActionEvent event) {
        if (selectedOrganisation != null) {
            try {
                dbAccessor.removeOrganisation(selectedOrganisation);
                showMessage("Организация успешно удалена!");
                settingsOrgTable.setItems(dbAccessor.getAllOrganisations());
            } catch (SQLException ex) {
                showMessage(ex.toString());
            }
        } else {
            showMessage("Организация не выбрана!");
        }
    }

    @FXML
    private void setDefaultOrganisation(ActionEvent event) {
        if (settingsDefaultOrgButton.getValue() != null) {
            try {
                dbAccessor.setDefaultOrganisation(settingsDefaultOrgButton.getValue());
                showMessage("Организация по умолчанию успешно изменена!");
            } catch (SQLException ex) {
                showMessage(ex.toString());
            }
        } else {
            showMessage("Организация не выбрана!");
        }
    }

    @FXML
    private void clearSelectionSettingsPane(MouseEvent event) {
        settingsUserAddButton.setText("Добавить");
        settingsOrgAddButton.setText("Добавить");
        settingsOrgAddCommentField.setText("");
        settingsOrgAddNameField.setText("");
        settingsOrgAddPhoneField.setText("");
        settingsOrgAddPriceField.setText("");
        settingsUserAddField.setText("");
        settingsUserTable.getSelectionModel().clearSelection();
        settingsOrgTable.getSelectionModel().clearSelection();
        settingsUserDeleteButton.setDisable(true);
        settingsOrgDeleteButton.setDisable(true);
        selectedUser = null;
        selectedOrganisation = null;
    }

    @FXML
    private void hideMessagePane(ActionEvent event) {
        messagePane.setVisible(false);
        messageTextField.setText("");
    }

}
