import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class QuestionnaireComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-questionnaire div table .btn-danger'));
  title = element.all(by.css('jhi-questionnaire div h2#page-heading span')).first();

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

export class QuestionnaireUpdatePage {
  pageTitle = element(by.id('jhi-questionnaire-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  typeSelect = element(by.id('field_type'));
  versionInput = element(by.id('field_version'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(timeout?: number) {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setVersionInput(version) {
    await this.versionInput.sendKeys(version);
  }

  async getVersionInput() {
    return await this.versionInput.getAttribute('value');
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

export class QuestionnaireDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-questionnaire-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-questionnaire'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
