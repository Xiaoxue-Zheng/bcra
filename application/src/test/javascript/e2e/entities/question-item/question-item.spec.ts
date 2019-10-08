/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { QuestionItemComponentsPage, QuestionItemDeleteDialog, QuestionItemUpdatePage } from './question-item.page-object';

const expect = chai.expect;

describe('QuestionItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionItemUpdatePage: QuestionItemUpdatePage;
  let questionItemComponentsPage: QuestionItemComponentsPage;
  /*let questionItemDeleteDialog: QuestionItemDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load QuestionItems', async () => {
    await navBarPage.goToEntity('question-item');
    questionItemComponentsPage = new QuestionItemComponentsPage();
    await browser.wait(ec.visibilityOf(questionItemComponentsPage.title), 5000);
    expect(await questionItemComponentsPage.getTitle()).to.eq('Question Items');
  });

  it('should load create QuestionItem page', async () => {
    await questionItemComponentsPage.clickOnCreateButton();
    questionItemUpdatePage = new QuestionItemUpdatePage();
    expect(await questionItemUpdatePage.getPageTitle()).to.eq('Create or edit a Question Item');
    await questionItemUpdatePage.cancel();
  });

  /* it('should create and save QuestionItems', async () => {
        const nbButtonsBeforeCreate = await questionItemComponentsPage.countDeleteButtons();

        await questionItemComponentsPage.clickOnCreateButton();
        await promise.all([
            questionItemUpdatePage.identifierSelectLastOption(),
            questionItemUpdatePage.setOrderInput('5'),
            questionItemUpdatePage.setLabelInput('label'),
            questionItemUpdatePage.questionSelectLastOption(),
        ]);
        expect(await questionItemUpdatePage.getOrderInput()).to.eq('5', 'Expected order value to be equals to 5');
        expect(await questionItemUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
        await questionItemUpdatePage.save();
        expect(await questionItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await questionItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last QuestionItem', async () => {
        const nbButtonsBeforeDelete = await questionItemComponentsPage.countDeleteButtons();
        await questionItemComponentsPage.clickOnLastDeleteButton();

        questionItemDeleteDialog = new QuestionItemDeleteDialog();
        expect(await questionItemDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Question Item?');
        await questionItemDeleteDialog.clickOnConfirmButton();

        expect(await questionItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
