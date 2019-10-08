/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AnswerGroupComponentsPage, AnswerGroupDeleteDialog, AnswerGroupUpdatePage } from './answer-group.page-object';

const expect = chai.expect;

describe('AnswerGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let answerGroupUpdatePage: AnswerGroupUpdatePage;
  let answerGroupComponentsPage: AnswerGroupComponentsPage;
  /*let answerGroupDeleteDialog: AnswerGroupDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AnswerGroups', async () => {
    await navBarPage.goToEntity('answer-group');
    answerGroupComponentsPage = new AnswerGroupComponentsPage();
    await browser.wait(ec.visibilityOf(answerGroupComponentsPage.title), 5000);
    expect(await answerGroupComponentsPage.getTitle()).to.eq('Answer Groups');
  });

  it('should load create AnswerGroup page', async () => {
    await answerGroupComponentsPage.clickOnCreateButton();
    answerGroupUpdatePage = new AnswerGroupUpdatePage();
    expect(await answerGroupUpdatePage.getPageTitle()).to.eq('Create or edit a Answer Group');
    await answerGroupUpdatePage.cancel();
  });

  /* it('should create and save AnswerGroups', async () => {
        const nbButtonsBeforeCreate = await answerGroupComponentsPage.countDeleteButtons();

        await answerGroupComponentsPage.clickOnCreateButton();
        await promise.all([
            answerGroupUpdatePage.setOrderInput('5'),
            answerGroupUpdatePage.answerSectionSelectLastOption(),
        ]);
        expect(await answerGroupUpdatePage.getOrderInput()).to.eq('5', 'Expected order value to be equals to 5');
        await answerGroupUpdatePage.save();
        expect(await answerGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await answerGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last AnswerGroup', async () => {
        const nbButtonsBeforeDelete = await answerGroupComponentsPage.countDeleteButtons();
        await answerGroupComponentsPage.clickOnLastDeleteButton();

        answerGroupDeleteDialog = new AnswerGroupDeleteDialog();
        expect(await answerGroupDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Answer Group?');
        await answerGroupDeleteDialog.clickOnConfirmButton();

        expect(await answerGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
