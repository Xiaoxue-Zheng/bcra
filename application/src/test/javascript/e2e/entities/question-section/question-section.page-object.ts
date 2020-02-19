import { element, by, ElementFinder } from 'protractor';

export class QuestionSectionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question-section div table .btn-danger'));
  title = element.all(by.css('jhi-question-section div h2#page-heading span')).first();
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

export class QuestionSectionUpdatePage {
  pageTitle = element(by.id('jhi-question-section-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  identifierSelect = element(by.id('field_identifier'));
  titleInput = element(by.id('field_title'));
  orderInput = element(by.id('field_order'));
  urlInput = element(by.id('field_url'));
  progressInput = element(by.id('field_progress'));

  questionnaireSelect = element(by.id('field_questionnaire'));
  questionGroupSelect = element(by.id('field_questionGroup'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setIdentifierSelect(identifier: string): Promise<void> {
    await this.identifierSelect.sendKeys(identifier);
  }

  async getIdentifierSelect(): Promise<string> {
    return await this.identifierSelect.element(by.css('option:checked')).getText();
  }

  async identifierSelectLastOption(): Promise<void> {
    await this.identifierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setOrderInput(order: string): Promise<void> {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput(): Promise<string> {
    return await this.orderInput.getAttribute('value');
  }

  async setUrlInput(url: string): Promise<void> {
    await this.urlInput.sendKeys(url);
  }

  async getUrlInput(): Promise<string> {
    return await this.urlInput.getAttribute('value');
  }

  async setProgressInput(progress: string): Promise<void> {
    await this.progressInput.sendKeys(progress);
  }

  async getProgressInput(): Promise<string> {
    return await this.progressInput.getAttribute('value');
  }

  async questionnaireSelectLastOption(): Promise<void> {
    await this.questionnaireSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async questionnaireSelectOption(option: string): Promise<void> {
    await this.questionnaireSelect.sendKeys(option);
  }

  getQuestionnaireSelect(): ElementFinder {
    return this.questionnaireSelect;
  }

  async getQuestionnaireSelectedOption(): Promise<string> {
    return await this.questionnaireSelect.element(by.css('option:checked')).getText();
  }

  async questionGroupSelectLastOption(): Promise<void> {
    await this.questionGroupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async questionGroupSelectOption(option: string): Promise<void> {
    await this.questionGroupSelect.sendKeys(option);
  }

  getQuestionGroupSelect(): ElementFinder {
    return this.questionGroupSelect;
  }

  async getQuestionGroupSelectedOption(): Promise<string> {
    return await this.questionGroupSelect.element(by.css('option:checked')).getText();
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

export class QuestionSectionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-questionSection-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-questionSection'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
