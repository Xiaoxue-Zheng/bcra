/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { QuestionnaireComponentsPage, QuestionnaireDeleteDialog, QuestionnaireUpdatePage } from './questionnaire.page-object';

const expect = chai.expect;

describe('Questionnaire e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionnaireUpdatePage: QuestionnaireUpdatePage;
  let questionnaireComponentsPage: QuestionnaireComponentsPage;
  let questionnaireDeleteDialog: QuestionnaireDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Questionnaires', async () => {
    await navBarPage.goToEntity('questionnaire');
    questionnaireComponentsPage = new QuestionnaireComponentsPage();
    await browser.wait(ec.visibilityOf(questionnaireComponentsPage.title), 5000);
    expect(await questionnaireComponentsPage.getTitle()).to.eq('Questionnaires');
  });

  it('should load create Questionnaire page', async () => {
    await questionnaireComponentsPage.clickOnCreateButton();
    questionnaireUpdatePage = new QuestionnaireUpdatePage();
    expect(await questionnaireUpdatePage.getPageTitle()).to.eq('Create or edit a Questionnaire');
    await questionnaireUpdatePage.cancel();
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
