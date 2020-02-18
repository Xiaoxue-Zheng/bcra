import { element, by, ElementFinder } from 'protractor';

export class QuestionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-question div table .btn-danger'));
  title = element.all(by.css('jhi-question div h2#page-heading span')).first();
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

export class QuestionUpdatePage {
  pageTitle = element(by.id('jhi-question-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  identifierSelect = element(by.id('field_identifier'));
  typeSelect = element(by.id('field_type'));
  orderInput = element(by.id('field_order'));
  textInput = element(by.id('field_text'));
  variableNameInput = element(by.id('field_variableName'));
  minimumInput = element(by.id('field_minimum'));
  maximumInput = element(by.id('field_maximum'));
  hintInput = element(by.id('field_hint'));
  hintTextInput = element(by.id('field_hintText'));

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

  async setTypeSelect(type: string): Promise<void> {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setOrderInput(order: string): Promise<void> {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput(): Promise<string> {
    return await this.orderInput.getAttribute('value');
  }

  async setTextInput(text: string): Promise<void> {
    await this.textInput.sendKeys(text);
  }

  async getTextInput(): Promise<string> {
    return await this.textInput.getAttribute('value');
  }

  async setVariableNameInput(variableName: string): Promise<void> {
    await this.variableNameInput.sendKeys(variableName);
  }

  async getVariableNameInput(): Promise<string> {
    return await this.variableNameInput.getAttribute('value');
  }

  async setMinimumInput(minimum: string): Promise<void> {
    await this.minimumInput.sendKeys(minimum);
  }

  async getMinimumInput(): Promise<string> {
    return await this.minimumInput.getAttribute('value');
  }

  async setMaximumInput(maximum: string): Promise<void> {
    await this.maximumInput.sendKeys(maximum);
  }

  async getMaximumInput(): Promise<string> {
    return await this.maximumInput.getAttribute('value');
  }

  async setHintInput(hint: string): Promise<void> {
    await this.hintInput.sendKeys(hint);
  }

  async getHintInput(): Promise<string> {
    return await this.hintInput.getAttribute('value');
  }

  async setHintTextInput(hintText: string): Promise<void> {
    await this.hintTextInput.sendKeys(hintText);
  }

  async getHintTextInput(): Promise<string> {
    return await this.hintTextInput.getAttribute('value');
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

export class QuestionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-question-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-question'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
