
async function getText(url) {
  site = await fetch(url);
  text = await site.text();
  return text;
}

async function setlang(lang) {
  blob = await getText(lang+".lang");
  single = blob.split("\n");
  p = document.getElementsByTagName('p');
  for(let i=0; i<p.length; i++) {
    p[i].textContent = single[i];
  }
}
