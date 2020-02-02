/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { QuestionSectionComponentsPage, QuestionSectionDeleteDialog, QuestionSectionUpdatePage } from './question-section.page-object';

const expect = chai.expect;

describe('QuestionSection e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionSectionUpdatePage: QuestionSectionUpdatePage;
  let questionSectionComponentsPage: QuestionSectionComponentsPage;
  /*let questionSectionDeleteDialog: QuestionSectionDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load QuestionSections', async () => {
    await navBarPage.goToEntity('question-section');
    questionSectionComponentsPage = new QuestionSectionComponentsPage();
    await browser.wait(ec.visibilityOf(questionSectionComponentsPage.title), 5000);
    expect(await questionSectionComponentsPage.getTitle()).to.eq('Question Sections');
  });

  it('should load create QuestionSection page', async () => {
    await questionSectionComponentsPage.clickOnCreateButton();
    questionSectionUpdatePage = new QuestionSectionUpdatePage();
    expect(await questionSectionUpdatePage.getPageTitle()).to.eq('Create or edit a Question Section');
    await questionSectionUpdatePage.cancel();
  });

  /* it('should create and save QuestionSections', async () => {
        const nbButtonsBeforeCreate = await questionSectionComponentsPage.countDeleteButtons();

        await questionSectionComponentsPage.clickOnCreateButton();
        await promise.all([
            questionSectionUpdatePage.identifierSelectLastOption(),
            questionSectionUpdatePage.setTitleInput('title'),
            questionSectionUpdatePage.setOrderInput('5'),
            questionSectionUpdatePage.questionnaireSelectLastOption(),
            questionSectionUpdatePage.questionGroupSelectLastOption(),
        ]);
        expect(await questionSectionUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');
        expect(await questionSectionUpdatePage.getOrderInput()).to.eq('5', 'Expected order value to be equals to 5');
        await questionSectionUpdatePage.save();
        expect(await questionSectionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await questionSectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last QuestionSection', async () => {
        const nbButtonsBeforeDelete = await questionSectionComponentsPage.countDeleteButtons();
        await questionSectionComponentsPage.clickOnLastDeleteButton();

        questionSectionDeleteDialog = new QuestionSectionDeleteDialog();
        expect(await questionSectionDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Question Section?');
        await questionSectionDeleteDialog.clickOnConfirmButton();

        expect(await questionSectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
