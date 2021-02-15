import { element, by, ElementFinder } from 'protractor';

export class StudyIdComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-study-id div table .btn-danger'));
  title = element.all(by.css('jhi-study-id div h2#page-heading span')).first();
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

export class StudyIdUpdatePage {
  pageTitle = element(by.id('jhi-study-id-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeInput = element(by.id('field_code'));

  participantSelect = element(by.id('field_participant'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
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

export class StudyIdDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-studyId-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-studyId'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
