
async function getText(url) {
  site = await fetch(url);
  text = await site.text();
  return text;
}

async function setlang(lang) {
  current = document.location.pathname.substring(document.location.pathname.lastIndexOf('/')+1,document.location.pathname.lastIndexOf('.'));
  blob = await getText(current+"-"+lang+".lang");
  single = blob.split("\n");
  p = document.getElementsByTagName('p');
  for(let i=0; i<p.length; i++) {
    p[i].innerHTML = single[i];
  }
}
