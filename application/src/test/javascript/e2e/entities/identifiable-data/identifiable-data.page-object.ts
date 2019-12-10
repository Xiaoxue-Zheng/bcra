import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class IdentifiableDataComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-identifiable-data div table .btn-danger'));
  title = element.all(by.css('jhi-identifiable-data div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class IdentifiableDataUpdatePage {
  pageTitle = element(by.id('jhi-identifiable-data-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nhsNumberInput = element(by.id('field_nhsNumber'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  firstnameInput = element(by.id('field_firstname'));
  surnameInput = element(by.id('field_surname'));
  emailInput = element(by.id('field_email'));
  address1Input = element(by.id('field_address1'));
  address2Input = element(by.id('field_address2'));
  address3Input = element(by.id('field_address3'));
  address4Input = element(by.id('field_address4'));
  address5Input = element(by.id('field_address5'));
  postcodeInput = element(by.id('field_postcode'));
  practiceNameInput = element(by.id('field_practiceName'));
  participantSelect = element(by.id('field_participant'));
  csvFileSelect = element(by.id('field_csvFile'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNhsNumberInput(nhsNumber) {
    await this.nhsNumberInput.sendKeys(nhsNumber);
  }

  async getNhsNumberInput() {
    return await this.nhsNumberInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth) {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput() {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  async setFirstnameInput(firstname) {
    await this.firstnameInput.sendKeys(firstname);
  }

  async getFirstnameInput() {
    return await this.firstnameInput.getAttribute('value');
  }

  async setSurnameInput(surname) {
    await this.surnameInput.sendKeys(surname);
  }

  async getSurnameInput() {
    return await this.surnameInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  async setAddress1Input(address1) {
    await this.address1Input.sendKeys(address1);
  }

  async getAddress1Input() {
    return await this.address1Input.getAttribute('value');
  }

  async setAddress2Input(address2) {
    await this.address2Input.sendKeys(address2);
  }

  async getAddress2Input() {
    return await this.address2Input.getAttribute('value');
  }

  async setAddress3Input(address3) {
    await this.address3Input.sendKeys(address3);
  }

  async getAddress3Input() {
    return await this.address3Input.getAttribute('value');
  }

  async setAddress4Input(address4) {
    await this.address4Input.sendKeys(address4);
  }

  async getAddress4Input() {
    return await this.address4Input.getAttribute('value');
  }

  async setAddress5Input(address5) {
    await this.address5Input.sendKeys(address5);
  }

  async getAddress5Input() {
    return await this.address5Input.getAttribute('value');
  }

  async setPostcodeInput(postcode) {
    await this.postcodeInput.sendKeys(postcode);
  }

  async getPostcodeInput() {
    return await this.postcodeInput.getAttribute('value');
  }

  async setPracticeNameInput(practiceName) {
    await this.practiceNameInput.sendKeys(practiceName);
  }

  async getPracticeNameInput() {
    return await this.practiceNameInput.getAttribute('value');
  }

  async participantSelectLastOption(timeout?: number) {
    await this.participantSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async participantSelectOption(option) {
    await this.participantSelect.sendKeys(option);
  }

  getParticipantSelect(): ElementFinder {
    return this.participantSelect;
  }

  async getParticipantSelectedOption() {
    return await this.participantSelect.element(by.css('option:checked')).getText();
  }

  async csvFileSelectLastOption(timeout?: number) {
    await this.csvFileSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async csvFileSelectOption(option) {
    await this.csvFileSelect.sendKeys(option);
  }

  getCsvFileSelect(): ElementFinder {
    return this.csvFileSelect;
  }

  async getCsvFileSelectedOption() {
    return await this.csvFileSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class IdentifiableDataDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-identifiableData-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-identifiableData'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
