import { element, by, ElementFinder } from 'protractor';

export class RiskComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-risk div table .btn-danger'));
  title = element.all(by.css('jhi-risk div h2#page-heading span')).first();
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

export class RiskUpdatePage {
  pageTitle = element(by.id('jhi-risk-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  lifetimeRiskInput = element(by.id('field_lifetimeRisk'));
  probNotBcraInput = element(by.id('field_probNotBcra'));
  probBcra1Input = element(by.id('field_probBcra1'));
  probBcra2Input = element(by.id('field_probBcra2'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setLifetimeRiskInput(lifetimeRisk: string): Promise<void> {
    await this.lifetimeRiskInput.sendKeys(lifetimeRisk);
  }

  async getLifetimeRiskInput(): Promise<string> {
    return await this.lifetimeRiskInput.getAttribute('value');
  }

  async setProbNotBcraInput(probNotBcra: string): Promise<void> {
    await this.probNotBcraInput.sendKeys(probNotBcra);
  }

  async getProbNotBcraInput(): Promise<string> {
    return await this.probNotBcraInput.getAttribute('value');
  }

  async setProbBcra1Input(probBcra1: string): Promise<void> {
    await this.probBcra1Input.sendKeys(probBcra1);
  }

  async getProbBcra1Input(): Promise<string> {
    return await this.probBcra1Input.getAttribute('value');
  }

  async setProbBcra2Input(probBcra2: string): Promise<void> {
    await this.probBcra2Input.sendKeys(probBcra2);
  }

  async getProbBcra2Input(): Promise<string> {
    return await this.probBcra2Input.getAttribute('value');
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

export class RiskDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-risk-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-risk'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
