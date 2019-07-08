document.addEventListener('DOMContentLoaded',
  () => {
    let elements = document.getElementsByTagName('element');
    for (let element of elements) {
      element.addEventListener('click',
        function() {
          element.children[0].click();
        }
      );
    }
  }
)
