import { element, by, ElementFinder } from 'protractor';

export class RiskAssessmentResultComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-risk-assessment-result div table .btn-danger'));
  title = element.all(by.css('jhi-risk-assessment-result div h2#page-heading span')).first();
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

export class RiskAssessmentResultUpdatePage {
  pageTitle = element(by.id('jhi-risk-assessment-result-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  filenameInput = element(by.id('field_filename'));

  participantSelect = element(by.id('field_participant'));
  individualRiskSelect = element(by.id('field_individualRisk'));
  populationRiskSelect = element(by.id('field_populationRisk'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setFilenameInput(filename: string): Promise<void> {
    await this.filenameInput.sendKeys(filename);
  }

  async getFilenameInput(): Promise<string> {
    return await this.filenameInput.getAttribute('value');
  }

  async participantSelectLastOption(): Promise<void> {
    await this.participantSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async participantSelectOption(option: string): Promise<void> {
    await this.participantSelect.sendKeys(option);
  }

  getParticipantSelect(): ElementFinder {
    return this.participantSelect;
  }

  async getParticipantSelectedOption(): Promise<string> {
    return await this.participantSelect.element(by.css('option:checked')).getText();
  }

  async individualRiskSelectLastOption(): Promise<void> {
    await this.individualRiskSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async individualRiskSelectOption(option: string): Promise<void> {
    await this.individualRiskSelect.sendKeys(option);
  }

  getIndividualRiskSelect(): ElementFinder {
    return this.individualRiskSelect;
  }

  async getIndividualRiskSelectedOption(): Promise<string> {
    return await this.individualRiskSelect.element(by.css('option:checked')).getText();
  }

  async populationRiskSelectLastOption(): Promise<void> {
    await this.populationRiskSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async populationRiskSelectOption(option: string): Promise<void> {
    await this.populationRiskSelect.sendKeys(option);
  }

  getPopulationRiskSelect(): ElementFinder {
    return this.populationRiskSelect;
  }

  async getPopulationRiskSelectedOption(): Promise<string> {
    return await this.populationRiskSelect.element(by.css('option:checked')).getText();
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

export class RiskAssessmentResultDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-riskAssessmentResult-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-riskAssessmentResult'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
