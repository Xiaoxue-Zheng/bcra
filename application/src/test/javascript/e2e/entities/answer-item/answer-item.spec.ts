/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AnswerItemComponentsPage, AnswerItemDeleteDialog, AnswerItemUpdatePage } from './answer-item.page-object';

const expect = chai.expect;

describe('AnswerItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let answerItemUpdatePage: AnswerItemUpdatePage;
  let answerItemComponentsPage: AnswerItemComponentsPage;
  /*let answerItemDeleteDialog: AnswerItemDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AnswerItems', async () => {
    await navBarPage.goToEntity('answer-item');
    answerItemComponentsPage = new AnswerItemComponentsPage();
    await browser.wait(ec.visibilityOf(answerItemComponentsPage.title), 5000);
    expect(await answerItemComponentsPage.getTitle()).to.eq('Answer Items');
  });

  it('should load create AnswerItem page', async () => {
    await answerItemComponentsPage.clickOnCreateButton();
    answerItemUpdatePage = new AnswerItemUpdatePage();
    expect(await answerItemUpdatePage.getPageTitle()).to.eq('Create or edit a Answer Item');
    await answerItemUpdatePage.cancel();
  });

  /* it('should create and save AnswerItems', async () => {
        const nbButtonsBeforeCreate = await answerItemComponentsPage.countDeleteButtons();

        await answerItemComponentsPage.clickOnCreateButton();
        await promise.all([
            answerItemUpdatePage.answerSelectLastOption(),
            answerItemUpdatePage.questionItemSelectLastOption(),
        ]);
        const selectedSelected = answerItemUpdatePage.getSelectedInput();
        if (await selectedSelected.isSelected()) {
            await answerItemUpdatePage.getSelectedInput().click();
            expect(await answerItemUpdatePage.getSelectedInput().isSelected(), 'Expected selected not to be selected').to.be.false;
        } else {
            await answerItemUpdatePage.getSelectedInput().click();
            expect(await answerItemUpdatePage.getSelectedInput().isSelected(), 'Expected selected to be selected').to.be.true;
        }
        await answerItemUpdatePage.save();
        expect(await answerItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await answerItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last AnswerItem', async () => {
        const nbButtonsBeforeDelete = await answerItemComponentsPage.countDeleteButtons();
        await answerItemComponentsPage.clickOnLastDeleteButton();

        answerItemDeleteDialog = new AnswerItemDeleteDialog();
        expect(await answerItemDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Answer Item?');
        await answerItemDeleteDialog.clickOnConfirmButton();

        expect(await answerItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
