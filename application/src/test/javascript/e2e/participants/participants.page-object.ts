import { $$, element, by, ElementFinder } from 'protractor';

export class ParticipantComponentsPage {
  searchButton = element(by.id('searchButton'));
  clearButton = element(by.id('clearButton'));
  studyIdInput = element(by.id('studyIdInput'));
  dateOfBirthInput = element(by.id('dateOfBirthInput'));
  statusSelector = element(by.id('statusSelector'));
  title = element.all(by.css('h1')).first();
  participantList = $$('tr');

  async clickSearchButton(): Promise<void> {
    await this.searchButton.click();
  }

  async clickClearButton(): Promise<void> {
    await this.clearButton.click();
  }

  async countParticipants(): Promise<number> {
    return await this.participantList.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }

  async setStudyId(code: string): Promise<void> {
    await this.studyIdInput.sendKeys(code);
  }

  async getStudyId(): Promise<string> {
    return await this.studyIdInput.getAttribute('value');
  }

  async setDateOfBirth(val: string): Promise<void> {
    await this.dateOfBirthInput.sendKeys(val);
  }

  async getDateOfBirth(): Promise<string> {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  async setSubmittedStatus(): Promise<void> {
    await this.statusSelector.sendKeys('SUBMITTED');
  }

  async getStatus(): Promise<string> {
    return await this.statusSelector.getAttribute('value');
  }
}
