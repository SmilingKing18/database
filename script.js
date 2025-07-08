document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.load-more').forEach(btn => {
        btn.addEventListener('click', () => {
            const grid = document.querySelector(btn.dataset.target);
            if (grid) {
                grid.querySelectorAll('.hidden').forEach(el => el.classList.remove('hidden'));
            }
            btn.remove();
        });
    });

    const modal = document.getElementById('modal');
    if (modal) {
        const modalBody = modal.querySelector('.modal-body');
        modal.querySelector('.close').addEventListener('click', () => {
            modal.style.display = 'none';
        });

        document.querySelectorAll('.card').forEach(card => {
            card.addEventListener('click', () => {
                const content = card.querySelector('.full-content').innerHTML;
                modalBody.innerHTML = content;
                modal.style.display = 'flex';
            });
        });
    }
});
