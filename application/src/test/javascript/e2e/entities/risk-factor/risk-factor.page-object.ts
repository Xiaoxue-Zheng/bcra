import { element, by, ElementFinder } from 'protractor';

export class RiskFactorComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-risk-factor div table .btn-danger'));
  title = element.all(by.css('jhi-risk-factor div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class RiskFactorUpdatePage {
  pageTitle = element(by.id('jhi-risk-factor-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  factorInput = element(by.id('field_factor'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setFactorInput(factor: string): Promise<void> {
    await this.factorInput.sendKeys(factor);
  }

  async getFactorInput(): Promise<string> {
    return await this.factorInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class RiskFactorDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-riskFactor-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-riskFactor'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
