require('dotenv').config({ path: '../../.env' });

export class Login {
    constructor(private page: any) { }

    async realizarLogin() {
        //Incluir campos de login.
        this.page.goto(process.env.SITE_AUTO || "");
        await this.page.locator('input[name="company"]').fill(process.env.COD_EMP || "");
        await this.page.locator('input[name="login"]').fill(process.env.USER || "");
        await this.page.locator('input[name="password"]').fill(process.env.PASS || "");
        //Clicar no botão de entrar.
        await this.page.getByRole('button', { name: 'Enviar' }).click();
        //Confirmação de login
        console.log("Login realizado!");
        return true;
    }
}