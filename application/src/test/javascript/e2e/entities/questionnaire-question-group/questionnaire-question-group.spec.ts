/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  QuestionnaireQuestionGroupComponentsPage,
  QuestionnaireQuestionGroupDeleteDialog,
  QuestionnaireQuestionGroupUpdatePage
} from './questionnaire-question-group.page-object';

const expect = chai.expect;

describe('QuestionnaireQuestionGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionnaireQuestionGroupUpdatePage: QuestionnaireQuestionGroupUpdatePage;
  let questionnaireQuestionGroupComponentsPage: QuestionnaireQuestionGroupComponentsPage;
  let questionnaireQuestionGroupDeleteDialog: QuestionnaireQuestionGroupDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load QuestionnaireQuestionGroups', async () => {
    await navBarPage.goToEntity('questionnaire-question-group');
    questionnaireQuestionGroupComponentsPage = new QuestionnaireQuestionGroupComponentsPage();
    await browser.wait(ec.visibilityOf(questionnaireQuestionGroupComponentsPage.title), 5000);
    expect(await questionnaireQuestionGroupComponentsPage.getTitle()).to.eq('Questionnaire Question Groups');
  });

  it('should load create QuestionnaireQuestionGroup page', async () => {
    await questionnaireQuestionGroupComponentsPage.clickOnCreateButton();
    questionnaireQuestionGroupUpdatePage = new QuestionnaireQuestionGroupUpdatePage();
    expect(await questionnaireQuestionGroupUpdatePage.getPageTitle()).to.eq('Create or edit a Questionnaire Question Group');
    await questionnaireQuestionGroupUpdatePage.cancel();
  });

  it('should create and save QuestionnaireQuestionGroups', async () => {
    const nbButtonsBeforeCreate = await questionnaireQuestionGroupComponentsPage.countDeleteButtons();

    await questionnaireQuestionGroupComponentsPage.clickOnCreateButton();
    await promise.all([
      questionnaireQuestionGroupUpdatePage.setUuidInput('uuid'),
      questionnaireQuestionGroupUpdatePage.setQuestionnaireUuidInput('questionnaireUuid'),
      questionnaireQuestionGroupUpdatePage.setQuestionGroupUuidInput('questionGroupUuid'),
      questionnaireQuestionGroupUpdatePage.setOrderInput('5'),
      questionnaireQuestionGroupUpdatePage.questionnaireSelectLastOption(),
      questionnaireQuestionGroupUpdatePage.questionGroupSelectLastOption()
    ]);
    expect(await questionnaireQuestionGroupUpdatePage.getUuidInput()).to.eq('uuid', 'Expected Uuid value to be equals to uuid');
    expect(await questionnaireQuestionGroupUpdatePage.getQuestionnaireUuidInput()).to.eq(
      'questionnaireUuid',
      'Expected QuestionnaireUuid value to be equals to questionnaireUuid'
    );
    expect(await questionnaireQuestionGroupUpdatePage.getQuestionGroupUuidInput()).to.eq(
      'questionGroupUuid',
      'Expected QuestionGroupUuid value to be equals to questionGroupUuid'
    );
    expect(await questionnaireQuestionGroupUpdatePage.getOrderInput()).to.eq('5', 'Expected order value to be equals to 5');
    await questionnaireQuestionGroupUpdatePage.save();
    expect(await questionnaireQuestionGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await questionnaireQuestionGroupComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last QuestionnaireQuestionGroup', async () => {
    const nbButtonsBeforeDelete = await questionnaireQuestionGroupComponentsPage.countDeleteButtons();
    await questionnaireQuestionGroupComponentsPage.clickOnLastDeleteButton();

    questionnaireQuestionGroupDeleteDialog = new QuestionnaireQuestionGroupDeleteDialog();
    expect(await questionnaireQuestionGroupDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Questionnaire Question Group?'
    );
    await questionnaireQuestionGroupDeleteDialog.clickOnConfirmButton();

    expect(await questionnaireQuestionGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
