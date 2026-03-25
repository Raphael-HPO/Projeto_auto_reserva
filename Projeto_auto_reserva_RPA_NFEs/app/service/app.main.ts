import { chromium } from "playwright";
import { Login } from "./actions/login.service";
import { Reserva } from "./actions/reserva.service";

async function executarRobo() {
    const browser = await chromium.launch({ headless: false });
    const page = await browser.newPage();
    const nPedido = '2260560460';

    const login = new Login(page);
    const consultaPedido = new Reserva(page);

    await login.realizarLogin();
    await consultaPedido.abrirMenu();
    await consultaPedido.buscarItensPedido(nPedido);
    await page.pause();
}

executarRobo();