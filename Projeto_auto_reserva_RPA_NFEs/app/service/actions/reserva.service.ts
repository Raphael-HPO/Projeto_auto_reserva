
export class Reserva {
    constructor(private page: any) { }
    async abrirMenu() {
        await this.page.locator('frame[name="main"]').
            contentFrame().
            getByText('Bem vindo ao Portal vSRM /').click();
        await this.page.locator('frame[name="header"]').
            contentFrame().
            getByText('Logística').hover();

        await this.page.locator('frame[name="main"]').
            contentFrame().
            getByRole('link', { name: 'Criar Solicitação de Reserva de Doca', exact: true }).click();
    }
    async buscarItensPedido(numeroDoPedido: string) {
        await this.page.locator('frame[name="main"]').
            contentFrame().
            locator('#newOrderNumber').click();

        const campoPedido = await this.page.locator('frame[name="main"]').
            contentFrame().
            locator('#newOrderNumber');
        campoPedido.fill(numeroDoPedido);
        campoPedido.click();
        campoPedido.press('Control');


        /*await this.page.locator('frame[name="main"]').
            contentFrame().
            getByRole('button', { name: 'Adicionar ' }).click();*/
    }
}