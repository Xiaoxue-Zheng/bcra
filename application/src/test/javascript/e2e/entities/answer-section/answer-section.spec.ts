/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AnswerSectionComponentsPage, AnswerSectionDeleteDialog, AnswerSectionUpdatePage } from './answer-section.page-object';

const expect = chai.expect;

describe('AnswerSection e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let answerSectionUpdatePage: AnswerSectionUpdatePage;
  let answerSectionComponentsPage: AnswerSectionComponentsPage;
  /*let answerSectionDeleteDialog: AnswerSectionDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AnswerSections', async () => {
    await navBarPage.goToEntity('answer-section');
    answerSectionComponentsPage = new AnswerSectionComponentsPage();
    await browser.wait(ec.visibilityOf(answerSectionComponentsPage.title), 5000);
    expect(await answerSectionComponentsPage.getTitle()).to.eq('Answer Sections');
  });

  it('should load create AnswerSection page', async () => {
    await answerSectionComponentsPage.clickOnCreateButton();
    answerSectionUpdatePage = new AnswerSectionUpdatePage();
    expect(await answerSectionUpdatePage.getPageTitle()).to.eq('Create or edit a Answer Section');
    await answerSectionUpdatePage.cancel();
  });

  /* it('should create and save AnswerSections', async () => {
        const nbButtonsBeforeCreate = await answerSectionComponentsPage.countDeleteButtons();

        await answerSectionComponentsPage.clickOnCreateButton();
        await promise.all([
            answerSectionUpdatePage.answerResponseSelectLastOption(),
            answerSectionUpdatePage.questionSectionSelectLastOption(),
        ]);
        await answerSectionUpdatePage.save();
        expect(await answerSectionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await answerSectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last AnswerSection', async () => {
        const nbButtonsBeforeDelete = await answerSectionComponentsPage.countDeleteButtons();
        await answerSectionComponentsPage.clickOnLastDeleteButton();

        answerSectionDeleteDialog = new AnswerSectionDeleteDialog();
        expect(await answerSectionDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Answer Section?');
        await answerSectionDeleteDialog.clickOnConfirmButton();

        expect(await answerSectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
