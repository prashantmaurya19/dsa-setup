//@ts-nocheck
import puppeteer from "puppeteer";
import { writeFile } from "fs/promises";
import { sleep } from "./util/time.js";
import yargs from "yargs";
import { hideBin } from "yargs/helpers";

// Parse command line arguments
const argv = yargs(hideBin(process.argv))
  .option("link", {
    alias: "l",
    description: "Problem URL to fetch",
    type: "string",
    demandOption: true,
  })
  .help().argv;

const link = argv.link;

// Launch the browser and open a new blank page.
const browser = await puppeteer.launch({
  executablePath: "/usr/bin/google-chrome",
  headless: false,
});
const page = await browser.newPage();

// Navigate the page to the provided URL
await page.goto(link);

await page.setViewport({ width: 1080, height: 1024 });

try {
  const { input, output } = await page.evaluate(() => {
    return {
      input: document.querySelector(".input pre").innerText,
      output: document.querySelector(".output pre").innerText,
    };
  });
} catch {
  const page1 = await browser.newPage();
  await page.goto(link);

  await page.setViewport({ width: 1080, height: 1024 });
  const { input, output } = await page.evaluate(() => {
    return {
      input: document.querySelector(".input pre").innerText,
      output: document.querySelector(".output pre").innerText,
    };
  });
}
await writeFile("answer.txt", output);
await writeFile("testcase.txt", input);

await browser.close();
