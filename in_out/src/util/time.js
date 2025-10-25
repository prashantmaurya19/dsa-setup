/**
 * @param {number} ms - miliseconds
 * @returns {Promise<void>}
 */
export function sleep(ms) {
  return new Promise((res, _rej) => setTimeout(res, ms));
}
