/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  QuestionGroupQuestionComponentsPage,
  QuestionGroupQuestionDeleteDialog,
  QuestionGroupQuestionUpdatePage
} from './question-group-question.page-object';

const expect = chai.expect;

describe('QuestionGroupQuestion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionGroupQuestionUpdatePage: QuestionGroupQuestionUpdatePage;
  let questionGroupQuestionComponentsPage: QuestionGroupQuestionComponentsPage;
  let questionGroupQuestionDeleteDialog: QuestionGroupQuestionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load QuestionGroupQuestions', async () => {
    await navBarPage.goToEntity('question-group-question');
    questionGroupQuestionComponentsPage = new QuestionGroupQuestionComponentsPage();
    await browser.wait(ec.visibilityOf(questionGroupQuestionComponentsPage.title), 5000);
    expect(await questionGroupQuestionComponentsPage.getTitle()).to.eq('Question Group Questions');
  });

  it('should load create QuestionGroupQuestion page', async () => {
    await questionGroupQuestionComponentsPage.clickOnCreateButton();
    questionGroupQuestionUpdatePage = new QuestionGroupQuestionUpdatePage();
    expect(await questionGroupQuestionUpdatePage.getPageTitle()).to.eq('Create or edit a Question Group Question');
    await questionGroupQuestionUpdatePage.cancel();
  });

  it('should create and save QuestionGroupQuestions', async () => {
    const nbButtonsBeforeCreate = await questionGroupQuestionComponentsPage.countDeleteButtons();

    await questionGroupQuestionComponentsPage.clickOnCreateButton();
    await promise.all([
      questionGroupQuestionUpdatePage.setUuidInput('uuid'),
      questionGroupQuestionUpdatePage.setOrderInput('5'),
      questionGroupQuestionUpdatePage.questionGroupSelectLastOption(),
      questionGroupQuestionUpdatePage.questionSelectLastOption()
    ]);
    expect(await questionGroupQuestionUpdatePage.getUuidInput()).to.eq('uuid', 'Expected Uuid value to be equals to uuid');
    expect(await questionGroupQuestionUpdatePage.getOrderInput()).to.eq('5', 'Expected order value to be equals to 5');
    await questionGroupQuestionUpdatePage.save();
    expect(await questionGroupQuestionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await questionGroupQuestionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last QuestionGroupQuestion', async () => {
    const nbButtonsBeforeDelete = await questionGroupQuestionComponentsPage.countDeleteButtons();
    await questionGroupQuestionComponentsPage.clickOnLastDeleteButton();

    questionGroupQuestionDeleteDialog = new QuestionGroupQuestionDeleteDialog();
    expect(await questionGroupQuestionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Question Group Question?');
    await questionGroupQuestionDeleteDialog.clickOnConfirmButton();

    expect(await questionGroupQuestionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
