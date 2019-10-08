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
  identifierSelect = element(by.id('field_identifier'));
  algorithmSelect = element(by.id('field_algorithm'));
  algorithmMinimumInput = element(by.id('field_algorithmMinimum'));
  algorithmMaximumInput = element(by.id('field_algorithmMaximum'));
  implementationVersionInput = element(by.id('field_implementationVersion'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setIdentifierSelect(identifier) {
    await this.identifierSelect.sendKeys(identifier);
  }

  async getIdentifierSelect() {
    return await this.identifierSelect.element(by.css('option:checked')).getText();
  }

  async identifierSelectLastOption(timeout?: number) {
    await this.identifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setAlgorithmSelect(algorithm) {
    await this.algorithmSelect.sendKeys(algorithm);
  }

  async getAlgorithmSelect() {
    return await this.algorithmSelect.element(by.css('option:checked')).getText();
  }

  async algorithmSelectLastOption(timeout?: number) {
    await this.algorithmSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setAlgorithmMinimumInput(algorithmMinimum) {
    await this.algorithmMinimumInput.sendKeys(algorithmMinimum);
  }

  async getAlgorithmMinimumInput() {
    return await this.algorithmMinimumInput.getAttribute('value');
  }

  async setAlgorithmMaximumInput(algorithmMaximum) {
    await this.algorithmMaximumInput.sendKeys(algorithmMaximum);
  }

  async getAlgorithmMaximumInput() {
    return await this.algorithmMaximumInput.getAttribute('value');
  }

  async setImplementationVersionInput(implementationVersion) {
    await this.implementationVersionInput.sendKeys(implementationVersion);
  }

  async getImplementationVersionInput() {
    return await this.implementationVersionInput.getAttribute('value');
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
