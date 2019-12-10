import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CsvFileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-csv-file div table .btn-danger'));
  title = element.all(by.css('jhi-csv-file div h2#page-heading span')).first();

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

export class CsvFileUpdatePage {
  pageTitle = element(by.id('jhi-csv-file-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  stateSelect = element(by.id('field_state'));
  statusInput = element(by.id('field_status'));
  fileNameInput = element(by.id('field_fileName'));
  uploadDatetimeInput = element(by.id('field_uploadDatetime'));
  contentSelect = element(by.id('field_content'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setStateSelect(state) {
    await this.stateSelect.sendKeys(state);
  }

  async getStateSelect() {
    return await this.stateSelect.element(by.css('option:checked')).getText();
  }

  async stateSelectLastOption(timeout?: number) {
    await this.stateSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setStatusInput(status) {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput() {
    return await this.statusInput.getAttribute('value');
  }

  async setFileNameInput(fileName) {
    await this.fileNameInput.sendKeys(fileName);
  }

  async getFileNameInput() {
    return await this.fileNameInput.getAttribute('value');
  }

  async setUploadDatetimeInput(uploadDatetime) {
    await this.uploadDatetimeInput.sendKeys(uploadDatetime);
  }

  async getUploadDatetimeInput() {
    return await this.uploadDatetimeInput.getAttribute('value');
  }

  async contentSelectLastOption(timeout?: number) {
    await this.contentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contentSelectOption(option) {
    await this.contentSelect.sendKeys(option);
  }

  getContentSelect(): ElementFinder {
    return this.contentSelect;
  }

  async getContentSelectedOption() {
    return await this.contentSelect.element(by.css('option:checked')).getText();
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

export class CsvFileDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-csvFile-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-csvFile'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
