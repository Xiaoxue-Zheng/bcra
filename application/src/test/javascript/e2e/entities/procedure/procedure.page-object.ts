import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ProcedureComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-procedure div table .btn-danger'));
  title = element.all(by.css('jhi-procedure div h2#page-heading span')).first();

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

export class ProcedureUpdatePage {
  pageTitle = element(by.id('jhi-procedure-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  consentResponseSelect = element(by.id('field_consentResponse'));
  riskAssesmentResponseSelect = element(by.id('field_riskAssesmentResponse'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async consentResponseSelectLastOption(timeout?: number) {
    await this.consentResponseSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async consentResponseSelectOption(option) {
    await this.consentResponseSelect.sendKeys(option);
  }

  getConsentResponseSelect(): ElementFinder {
    return this.consentResponseSelect;
  }

  async getConsentResponseSelectedOption() {
    return await this.consentResponseSelect.element(by.css('option:checked')).getText();
  }

  async riskAssesmentResponseSelectLastOption(timeout?: number) {
    await this.riskAssesmentResponseSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async riskAssesmentResponseSelectOption(option) {
    await this.riskAssesmentResponseSelect.sendKeys(option);
  }

  getRiskAssesmentResponseSelect(): ElementFinder {
    return this.riskAssesmentResponseSelect;
  }

  async getRiskAssesmentResponseSelectedOption() {
    return await this.riskAssesmentResponseSelect.element(by.css('option:checked')).getText();
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

export class ProcedureDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-procedure-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-procedure'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}