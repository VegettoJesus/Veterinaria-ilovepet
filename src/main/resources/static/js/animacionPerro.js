const perro = document.getElementById('perro')
const inputUsuario = document.getElementById('username')
const inputPassword = document.getElementById('password')
const body = document.querySelector('body')
const anchoMitad = window.innerWidth/2;
const altoMitad = window.innerHeight/2;
let seguirPunteroMouse = true;

body.addEventListener('mousemove',(m)=> {
    if(seguirPunteroMouse){
        if(m.clientX < anchoMitad && m.clientY < altoMitad){
            perro.src = "imagenes/perro_puntero_2.png"
        }
        else if(m.clientX < anchoMitad && m.clientY > altoMitad){
            perro.src = "imagenes/perro_puntero_3.png"
        }
        else if(m.clientX > anchoMitad && m.clientY < altoMitad){
            perro.src = "imagenes/perro_puntero_5.png"
        }else{
            perro.src = "imagenes/perro_puntero_4.png"
        }
    }  
})

inputUsuario.addEventListener('focus',()=>{
    seguirPunteroMouse = false;
})
inputUsuario.addEventListener('blur',()=>{
    seguirPunteroMouse = true;
})

inputUsuario.addEventListener('keyup',()=>{
    let usuario = inputUsuario.value.length;
    console.log(inputUsuario.value.length)
    if(usuario >= 0 && usuario <=12){
        perro.src = "imagenes/perro_leyendo_1.png"
    }
    else if(usuario >= 13 && usuario <=46){
        perro.src = "imagenes/perro_leyendo_2.png"
    }
    else{
        perro.src = "imagenes/perro_leyendo_3.png"
    }
})

inputPassword.addEventListener('focus',()=>{
    seguirPunteroMouse = false;
    perro.src = "imagenes/perro_sin_mirar.png"
})
inputPassword.addEventListener('blur',()=>{
    seguirPunteroMouse = true;
})

/*MEDIR EL TAMAÑO DE PANTALLA
console.log(window.innerWidth)
console.log(window.innerHeight)*/